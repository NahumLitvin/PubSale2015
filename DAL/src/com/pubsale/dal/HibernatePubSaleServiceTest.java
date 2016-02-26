package com.pubsale.dal;

import dto.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;


public class HibernatePubSaleServiceTest {

    private static final String PHONE = "0545796938";
    private static final String NAHUM_LITVIN = "Nahum Litvin";
    private static final String PASSWORD = "password";
    private static final String LNAHUM_GMAIL_COM = "lnahum@gmail.com";
    HibernatePubSaleService service;

    @Before
    public void setUp() throws Exception {
        service = new HibernatePubSaleService();
    }

    @After
    public void tearDown() throws Exception {
        service.Close();
        service = null;
    }

    @Test
    public void FirstRegisterOK() {
        RegisterResponse res = RegisterNahum();
        Assert.assertEquals(RegisterStatus.OK, res.getStatus());
    }

    @Test
    public void DoubleRegisterError() {
        RegisterNahum();
        RegisterResponse res = RegisterNahum();
        Assert.assertEquals(RegisterStatus.UserExists, res.getStatus());
    }


    @Test
    public void LoginFailsIfNotRegistered() {
        LoginResponse loginNahum = LoginNahum();
        Assert.assertEquals(null, loginNahum.getSession());
    }

    @Test
    public void LoginSuccedesIfRegistered() {
        LoginResponse loginNahum = RegisterAndLoginNahum();
        Assert.assertNotEquals(null, loginNahum.getSession());
    }

    @Test
    public void IsLoggedInFailsIfBadSession() {
        IsLoggedInResponse isLoggedIn = service.IsLoggedIn(new IsLoggedInRequest(UUID.randomUUID().toString(), LNAHUM_GMAIL_COM));
        Assert.assertFalse(isLoggedIn.isLoggedIn());
    }

    @Test
    public void IsLoggedInTrueIfSessionIsOk() {
        LoginResponse loginNahum = RegisterAndLoginNahum();
        IsLoggedInResponse isLoggedIn = service.IsLoggedIn(new IsLoggedInRequest(loginNahum.getSession().toString(), LNAHUM_GMAIL_COM));
        Assert.assertTrue(isLoggedIn.isLoggedIn());
    }

    private LoginResponse RegisterAndLoginNahum() {
        RegisterNahum();
        LoginResponse loginNahum = LoginNahum();
        return loginNahum;
    }

    private RegisterResponse RegisterNahum() {
        RegisterRequest request = new RegisterRequest(LNAHUM_GMAIL_COM, PASSWORD, NAHUM_LITVIN, PHONE);
        return service.Register(request);

    }

    private LoginResponse LoginNahum() {
        LoginRequest request = new LoginRequest(LNAHUM_GMAIL_COM, PASSWORD);
        return service.Login(request);

    }

}
