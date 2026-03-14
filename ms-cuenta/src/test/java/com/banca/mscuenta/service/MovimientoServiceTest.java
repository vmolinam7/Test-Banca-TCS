package com.banca.mscuenta.service;

import com.banca.mscuenta.exception.SaldoNoDisponibleException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovimientoServiceTest {

    @Test
    void saldoNoDisponibleTest(){

        assertThrows(
                SaldoNoDisponibleException.class,
                () -> {
                    throw new SaldoNoDisponibleException("Saldo no disponible");
                }
        );

    }

}