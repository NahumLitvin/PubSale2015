package com.pubsale.dal;

import com.pubsale.PasswordHash;
import com.pubsale.dto.*;
import com.pubsale.interfaces.IPubSaleService;
import org.jinq.jpa.JinqJPAStreamProvider;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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
        //entityManagerFactory.close();
        streams = null;
        em.close();
    }

    @Override
    public IsLoggedInResponse IsLoggedIn(IsLoggedInRequest request) {

        String s = request.getSession();
        Optional<SessionDAO> session = streams.streamAll(em, SessionDAO.class)
                .where(x -> (Objects.equals(x.getSession(), s))).findOne();

        if (!session.isPresent())
            return new IsLoggedInResponse(false);
        boolean IsEmailOK = Objects.equals(request.getEmail(), session.get().getUser().getEmail());
        boolean IsDateOk = Calendar.getInstance().getTime().compareTo(session.get().getExpirationDate()) < 0;
        IsLoggedInResponse res = new IsLoggedInResponse(IsEmailOK && IsDateOk);
        return res;
    }

    @Override
    public LoginResponse Login(LoginRequest request) {
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

                return new LoginResponse(createSession(user.get()).getSession());

            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                break;
            }
        } while (false);

        // check hash create new session id add to db and return session id.
        return new LoginResponse(null);
    }

    @Override
    public RegisterResponse Register(RegisterRequest request) {

        try {

            if (isUserRegistered(request))
                return new RegisterResponse(RegisterStatus.UserExists);
            registerUser(request);
            return new RegisterResponse(RegisterStatus.OK);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException | EntityExistsException
                | IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new RegisterResponse(RegisterStatus.Exception);
        }

    }

    @Override
    public CreateAuctionResponse CreateAuction(CreateAuctionRequest request) {
        if (!IsLoggedIn(request.getIsLoggedIn()).isLoggedIn()) return new CreateAuctionResponse(false, "not logged in");
        em.getTransaction().begin();
        AuctionDAO auction = modelMapper.map(request.getAuction(), AuctionDAO.class);
        em.persist(auction);
        em.getTransaction().commit();
        return null;
    }

    private void registerUser(RegisterRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException {
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

    private Boolean isUserRegistered(RegisterRequest request) {
        String email = request.getEmail();
        Optional<UserDAO> user = streams.streamAll(em, UserDAO.class).where(x -> (Objects.equals(x.getEmail(), email)))
                .findOne();

        return user.isPresent();
    }


}
