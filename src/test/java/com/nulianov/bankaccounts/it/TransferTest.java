package com.nulianov.bankaccounts.it;

import com.despegar.http.client.DeleteMethod;
import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpResponse;
import com.despegar.http.client.PostMethod;
import com.despegar.sparkjava.test.SparkServer;
import com.google.gson.Gson;
import com.nulianov.bankaccounts.Application;
import com.nulianov.bankaccounts.domain.Account;
import com.nulianov.bankaccounts.domain.Transfer;
import org.junit.*;
import spark.servlet.SparkApplication;

import java.math.BigDecimal;

public class TransferTest {
    private static final Account from = new Account("Ivan", "Ivanov", new BigDecimal(10.5));
    private static final Account to = new Account("Petr", "Petrov", new BigDecimal(0.1));
    private static final Account validAccount = new Account("Carl", "Sagan", new BigDecimal(10.1));
    private static final Account duplicationAccount = new Account(to.getId(), "Petr", "Petrov", new BigDecimal(0.1));
    private static final Account absentAccount = new Account("John", "Doe", new BigDecimal(0.1));

    public static class TestApplication implements SparkApplication {
        @Override
        public void init() {
            new Application().main(null);
        }
    }

    @ClassRule
    public static SparkServer<TestApplication> testServer = new SparkServer<>(TestApplication.class, 4567);

    @BeforeClass
    public static void init() throws Exception {
        PostMethod postFrom = testServer.post("/api/account", new Gson().toJson(from), false);
        PostMethod postTo = testServer.post("/api/account", new Gson().toJson(to), false);

        testServer.execute(postFrom);
        testServer.execute(postTo);
    }

    @Test
    public void transferSufficientAmount() throws Exception {
        Transfer transfer = new Transfer(from.getId(), to.getId(), new BigDecimal(10));
        PostMethod post = testServer.post("/api/transfer", new Gson().toJson(transfer), false);

        HttpResponse httpResponse = testServer.execute(post);
        Assert.assertEquals(200, httpResponse.code());
    }

    @Test
    public void transferInsufficientAmount() throws Exception {
        Transfer transfer = new Transfer(from.getId(), to.getId(), new BigDecimal(1000));
        PostMethod post = testServer.post("/api/transfer", new Gson().toJson(transfer), false);

        HttpResponse httpResponse = testServer.execute(post);
        Assert.assertEquals(400, httpResponse.code());
    }

    @Test
    public void transferToAbsentAccount() throws Exception {
        Transfer transfer = new Transfer(from.getId(), absentAccount.getId(), new BigDecimal(1000));
        PostMethod post = testServer.post("/api/transfer", new Gson().toJson(transfer), false);

        HttpResponse httpResponse = testServer.execute(post);
        Assert.assertEquals(404, httpResponse.code());
    }

    @Test
    public void createAccountWithDuplicatedId() throws Exception {
        PostMethod post = testServer.post("/api/account", new Gson().toJson(duplicationAccount), false);

        HttpResponse httpResponse = testServer.execute(post);
        Assert.assertEquals(400, httpResponse.code());
    }

    @Test
    public void createValidAccount() throws Exception {
        PostMethod post = testServer.post("/api/account", new Gson().toJson(validAccount), false);

        HttpResponse httpResponse = testServer.execute(post);
        Assert.assertEquals(201, httpResponse.code());
    }

    @Test
    public void getAbsentAccount() throws Exception {
        GetMethod get = testServer.get("/api/account/" + absentAccount.getId(), false);

        HttpResponse httpResponse = testServer.execute(get);
        Assert.assertEquals(404, httpResponse.code());
    }

    @Test
    public void getExistingAccount() throws Exception {
        GetMethod get = testServer.get("/api/account/" + from.getId(), false);

        HttpResponse httpResponse = testServer.execute(get);
        Assert.assertEquals(200, httpResponse.code());
    }

    @AfterClass
    public static void tearDown() throws Exception {
        DeleteMethod deleteFrom = testServer.delete("/api/account/" + from.getId(), false);
        DeleteMethod deleteTo = testServer.delete("/api/account/" + to.getId(), false);

        testServer.execute(deleteFrom);
        testServer.execute(deleteTo);
    }
}
