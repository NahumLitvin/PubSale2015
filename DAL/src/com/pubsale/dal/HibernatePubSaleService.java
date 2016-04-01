package com.pubsale.dal;

import com.pubsale.PasswordHash;
import com.pubsale.dto.*;
import com.pubsale.interfaces.IPubSaleService;
import org.jinq.jpa.JinqJPAStreamProvider;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

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
    public IsActionSuccededDTO CreateAuction(CreateAuctionRequestDTO request) {
        if (!IsLoggedIn(request.getIsLoggedIn()).isLoggedIn())
            return new IsActionSuccededDTO(false, "not logged in");


        try {
            em.getTransaction().begin();
            AuctionDAO auction = modelMapper.map(request.getAuction(), AuctionDAO.class);
            auction.setSeller(getUser(request.getIsLoggedIn().getEmail()).get());
            em.persist(auction);
            em.getTransaction().commit();
        } catch (Exception ex) {
            return new IsActionSuccededDTO(false, ex.getMessage());
        }

        return new IsActionSuccededDTO(true, "");
    }

    @Override
    public List<AuctionDTO> GetAuctions(GetAuctionsRequestDTO request) {
        List<AuctionDAO> list = streams.streamAll(em, AuctionDAO.class).toList();
        Type listType = new TypeToken<List<AuctionDTO>>() {
        }.getType();
        return modelMapper.map(list, listType);
    }

    @Override
    public List<CategoryDTO> GetCategories() {

        List<CategoryDAO> list = streams.streamAll(em, CategoryDAO.class).toList();
        Type listType = new TypeToken<List<CategoryDTO>>() {
        }.getType();
        return modelMapper.map(list, listType);
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
