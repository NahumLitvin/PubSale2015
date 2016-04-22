package com.pubsale.dal;

import com.pubsale.PasswordHash;
import com.pubsale.dto.*;
import com.pubsale.interfaces.IPubSaleService;
import org.jinq.jpa.JPAJinqStream;
import org.jinq.jpa.JinqJPAStreamProvider;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import retrofit.http.Body;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Type;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

public class HibernatePubSaleService implements IPubSaleService {

    ModelMapper modelMapper;
    private JinqJPAStreamProvider streams;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager em;

    public HibernatePubSaleService() {
        entityManagerFactory = Persistence.createEntityManagerFactory("PubSale.DAL");
        streams = new JinqJPAStreamProvider(entityManagerFactory);
        em = entityManagerFactory.createEntityManager();
        modelMapper = new ModelMapper();
    }

    public void Close() {
        em.close();
    }

    @Override
    public IsLoggedInResponseDTO IsLoggedIn(IsLoggedInRequestDTO request) {

        String s = request.getSession();
        Optional<SessionDAO> session = streams.streamAll(em, SessionDAO.class)
                .where(x -> (Objects.equals(x.getSession(), s))).findOne();

        if (!session.isPresent())
            return new IsLoggedInResponseDTO() {{
                setLoggedIn(false);
            }};

        boolean IsEmailOK = Objects.equals(request.getEmail(), session.get().getUser().getEmail());
        boolean IsDateOk = Calendar.getInstance().getTime().compareTo(session.get().getExpirationDate()) < 0;
        IsLoggedInResponseDTO res = new IsLoggedInResponseDTO() {{
            setLoggedIn(IsEmailOK && IsDateOk);
        }};
        return res;
    }

    @Override
    public LoginResponseDTO Login(LoginRequestDTO request) {
        String email = request.getEmail();
        do {
            try {
                Optional<UserDAO> user = streams.streamAll(em, UserDAO.class)
                        .where(x -> (Objects.equals(x.getEmail(), email))).findOne();
                if (!user.isPresent())
                    break;

                if (!PasswordHash.validatePassword(request.getPassword(),
                        user.get().getSaltedHashedPassword()))
                    break;

                return new LoginResponseDTO(createSession(user.get()).getSession());

            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                break;
            }
        } while (false);

        // check hash create new session id add to db and return session id.
        return new LoginResponseDTO(null);
    }

    @Override
    public RegisterResponseDTO Register(RegisterRequestDTO request) {

        try {

            if (isUserRegistered(request))
                return new RegisterResponseDTO(RegisterStatusDTO.UserExists);
            registerUser(request);
            return new RegisterResponseDTO(RegisterStatusDTO.OK);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new RegisterResponseDTO(RegisterStatusDTO.Exception);
        }

    }

    @Override
    public IsActionSucceededDTO CreateAuction(CreateAuctionRequestDTO request) {
        if (!IsLoggedIn(request.getIsLoggedIn()).isLoggedIn())
            return new IsActionSucceededDTO(false, "not logged in");


        try {
            em.getTransaction().begin();
            AuctionDAO auction = modelMapper.map(request.getAuction(), AuctionDAO.class);
            auction.setSeller(getUser(request.getIsLoggedIn().getEmail()).get());
            em.persist(auction);
            em.getTransaction().commit();
        } catch (Exception ex) {
            return new IsActionSucceededDTO(false, ex.getMessage());
        }

        return new IsActionSucceededDTO(true, "");
    }

    @Override
    public List<AuctionDTO> GetAuctions(GetAuctionsRequestDTO request) {

        JPAJinqStream<AuctionDAO> stream = streams.streamAll(em, AuctionDAO.class);
        String freeText = request.getFreeText();
        if (freeText != null && !freeText.isEmpty())
            stream = stream.where(x -> x.getName().contains(freeText) || x.getDescription().contains(freeText));

        String sellerEmail = request.getSellerEmail();
        if (sellerEmail != null && !sellerEmail.isEmpty())
            stream = stream.where(x -> x.getSeller().getEmail().equals(sellerEmail));

        CategoryDTO category = request.getCategory();
        if (category != null) {
            Integer id = category.getId();
            stream = stream.where(x -> x.getCategory().getId() == id);
        }

        long after = request.getEndDateAfterUnixTime();
        long before = request.getEndDateBeforeUnixTime();

        if (after != 0) {
            stream = stream.where(x -> x.getEndUnixTime() > after);
        }

        if (before != 0) {
            stream = stream.where(x -> x.getEndUnixTime() < before);
        }
        Type listType = new TypeToken<List<AuctionDTO>>() {
        }.getType();

        return modelMapper.map(stream.sortedDescendingBy(x -> x.getEndUnixTime()).toList(), listType);
    }

    @Override
    public List<CategoryDTO> GetCategories() {

        List<CategoryDAO> list = streams.streamAll(em, CategoryDAO.class).toList();
        Type listType = new TypeToken<List<CategoryDTO>>() {
        }.getType();
        return modelMapper.map(list, listType);
    }

    @Override
    public synchronized IsActionSucceededDTO BidInAuction(@Body BidRequestDTO request) {
        IsActionSucceededDTO dto = new IsActionSucceededDTO();
        int id = request.getAuctionId();

        if (!IsLoggedIn(request.getRequest()).isLoggedIn()) {
            dto.setFailReason("user is not logged in");
            return dto;
        }

        //valid session get item
        JPAJinqStream<AuctionDAO> stream = streams.streamAll(em, AuctionDAO.class);
        stream = stream.where(x -> x.getId() == id);
        final Optional<AuctionDAO> one = stream.findOne();

        if (!one.isPresent()) {
            dto.setFailReason("item doesn't exist");
            return dto;
        }

        final AuctionDAO auction = one.get();
        if (auction.getEndUnixTime() * 1000L <= System.currentTimeMillis()) {
            dto.setFailReason("auction has ended.");
            return dto;
        }

        if (Objects.equals(auction.getSeller().getEmail(), request.getRequest().getEmail())) {
            dto.setFailReason("cannot buy your own item.");
            return dto;
        }

        if (auction.getEndPrice() != 0 && request.getBidValue() > one.get().getEndPrice()) {
            dto.setFailReason("cannot place bid over 'buy now' price");
            return dto;
        }

        if (auction.getCurrentPrice() > request.getBidValue()) {
            dto.setFailReason("bid must be greater than current price");
            return dto;
        }

        //buy now bid
        if (auction.getEndPrice() != 0 && request.getBidValue() == auction.getEndPrice()) {
            em.getTransaction().begin();
            auction.setTopBidder(getUser(request.getRequest().getEmail()).get());
            auction.setCurrentPrice(auction.getEndPrice());
            auction.setEndUnixTime(System.currentTimeMillis() / 1000L);
            em.persist(auction);
            em.getTransaction().commit();
            return dto;
        }
        // regular bid
        em.getTransaction().begin();
        auction.setTopBidder(getUser(request.getRequest().getEmail()).get());
        auction.setCurrentPrice(request.getBidValue());
        em.persist(auction);
        em.getTransaction().commit();

        return dto;
    }

    @Override
    public UserDTO GetWinnerInAuction(@Body GetWinnerInAuctionDTO request) {
        if (IsLoggedIn(request.getIsLoggedIn()).isLoggedIn()) {
            return null;
        }
        UserDTO resposne = new UserDTO();
        JPAJinqStream<AuctionDAO> stream = streams.streamAll(em, AuctionDAO.class);
        int id = request.getAuctionId();
        stream = stream.where(x -> x.getId() == id);
        final Optional<AuctionDAO> one = stream.findOne();
        if (!one.isPresent()) return null;
        AuctionDAO auction = one.get();
        resposne.setName(auction.getTopBidder().getName());
        resposne.setPhone(auction.getTopBidder().getPhone());
        return resposne;
    }

    private void registerUser(RegisterRequestDTO request) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String hashedandsaltedpassword = PasswordHash.createHash(request.getPassword());
        em.getTransaction().begin();
        UserDAO newuser = new UserDAO(request, hashedandsaltedpassword);
        em.persist(newuser);
        em.getTransaction().commit();
    }

    private SessionDAO createSession(UserDAO user) {
        em.getTransaction().begin();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        SessionDAO session = new SessionDAO(UUID.randomUUID().toString(), user, calendar.getTime());
        em.persist(session);
        em.getTransaction().commit();
        return session;
    }

    private Boolean isUserRegistered(RegisterRequestDTO request) {
        return getUser(request.getEmail()).isPresent();
    }

    private Optional<UserDAO> getUser(String email) {
        Optional<UserDAO> user = streams.streamAll(em, UserDAO.class).where(x -> (Objects.equals(x.getEmail(), email)))
                .findOne();
        return user;
    }


}
