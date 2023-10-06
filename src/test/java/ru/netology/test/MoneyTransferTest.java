package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.CardBalance;
import ru.netology.page.LoginPage;
import ru.netology.page.TransactionPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.getFirstCardNumber;
import static ru.netology.data.DataHelper.getSecondCardNumber;

public class MoneyTransferTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val cardBalance = verificationPage.validVerify(verificationCode);
    }

    @Test
    public void shouldTransferFrom1To2() {
        int amount = 7000;
        val cardBalance = new CardBalance();
        val firstCardBalanceStart = cardBalance.getFirstCardBalance();
        val secondCardBalanceStart = cardBalance.getSecondCardBalance();
        Object transactionPage;
        transactionPage = cardBalance.pushSecondCardButton();
        ((TransactionPage) transactionPage).transferMoney(amount, getFirstCardNumber());
        val firstCardBalanceFinish = firstCardBalanceStart - amount;
        val secondCardBalanceFinish = secondCardBalanceStart + amount;
        assertEquals(firstCardBalanceFinish, cardBalance.getFirstCardBalance());
        assertEquals(secondCardBalanceFinish, cardBalance.getSecondCardBalance());
    }

    @Test
    public void shouldTransferFrom2To1() {
        int amount = 3800;
        val cardBalance = new CardBalance();
        val firstCardBalanceStart = cardBalance.getFirstCardBalance();
        val secondCardBalanceStart = cardBalance.getSecondCardBalance();
        val transactionPage = cardBalance.pushFirstCardButton();
        transactionPage.transferMoney(amount, getSecondCardNumber());
        val firstCardBalanceFinish = firstCardBalanceStart + amount;
        val secondCardBalanceFinish = secondCardBalanceStart - amount;
        assertEquals(firstCardBalanceFinish, cardBalance.getFirstCardBalance());
        assertEquals(secondCardBalanceFinish, cardBalance.getSecondCardBalance());
    }


}