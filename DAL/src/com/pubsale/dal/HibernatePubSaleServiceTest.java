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
        RegisterResponseDTO res = RegisterNahum();
        Assert.assertEquals(RegisterStatusDTO.OK, res.getStatus());
    }

    @Test
    public void DoubleRegisterError() {
        RegisterNahum();
        RegisterResponseDTO res = RegisterNahum();
        Assert.assertEquals(RegisterStatusDTO.UserExists, res.getStatus());
    }


    @Test
    public void LoginFailsIfNotRegistered() {
        LoginResponseDTO loginNahum = LoginNahum();
        Assert.assertEquals(null, loginNahum.getSession());
    }

    @Test
    public void LoginSuccedesIfRegistered() {
        LoginResponseDTO loginNahum = RegisterAndLoginNahum();
        Assert.assertNotEquals(null, loginNahum.getSession());
    }

    @Test
    public void IsLoggedInFailsIfBadSession() {
        IsLoggedInResponseDTO isLoggedIn = service.IsLoggedIn(new IsLoggedInRequestDTO(UUID.randomUUID().toString(), LNAHUM_GMAIL_COM));
        Assert.assertFalse(isLoggedIn.isLoggedIn());
    }

    @Test
    public void IsLoggedInTrueIfSessionIsOk() {
        LoginResponseDTO loginNahum = RegisterAndLoginNahum();
        IsLoggedInResponseDTO isLoggedIn = service.IsLoggedIn(new IsLoggedInRequestDTO(loginNahum.getSession().toString(), LNAHUM_GMAIL_COM));
        Assert.assertTrue(isLoggedIn.isLoggedIn());
    }

    private LoginResponseDTO RegisterAndLoginNahum() {
        RegisterNahum();
        LoginResponseDTO loginNahum = LoginNahum();
        return loginNahum;
    }

    private RegisterResponseDTO RegisterNahum() {
        RegisterRequestDTO request = new RegisterRequestDTO(LNAHUM_GMAIL_COM, PASSWORD, NAHUM_LITVIN, PHONE);
        return service.Register(request);

    }

    private LoginResponseDTO LoginNahum() {
        LoginRequestDTO request = new LoginRequestDTO(LNAHUM_GMAIL_COM, PASSWORD);
        return service.Login(request);

    }

}
