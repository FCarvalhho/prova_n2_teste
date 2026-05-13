package com.hyperminetec.livro.e2e;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FluxoCompraE2ETest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    private void pausaDramatica(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void deveExecutarFluxoDeCompraComoCliente() {
        driver.get("http://localhost:5173/cadastro");
        pausaDramatica(1500);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='text']")))
                .sendKeys("Robô Comprador");

        String emailRobo = "robo" + System.currentTimeMillis() + "@vendas.com";
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys(emailRobo);
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("senha123");
        pausaDramatica(1000);

        driver.findElement(By.xpath("//button[@type='submit']")).click();

        wait.until(ExpectedConditions.urlContains("/login"));
        pausaDramatica(1000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")))
                .sendKeys(emailRobo);
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("senha123");
        pausaDramatica(1000);
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        WebElement btnAdicionarCarrinho = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//button[contains(text(), 'Adicionar ao Carrinho')])[1]")
        ));
        pausaDramatica(1500);
        btnAdicionarCarrinho.click();

        WebElement btnFinalizar = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Finalizar Pedido')]")
        ));
        pausaDramatica(1500);
        btnFinalizar.click();

        Alert alertaCompra = wait.until(ExpectedConditions.alertIsPresent());
        Assertions.assertTrue(alertaCompra.getText().contains("Pedido finalizado com sucesso!"));
        pausaDramatica(1000);
        alertaCompra.accept();
        pausaDramatica(1000);
    }
    
    @Test
    void deveExecutarFluxoDeAdministracaoComoAdmin() {
        
        driver.get("http://localhost:5173/login");
        pausaDramatica(1500);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")))
                .sendKeys("admin@vendas.com");
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("admin123");
        pausaDramatica(1000);
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        WebElement btnEditar = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//button[text()='✎'])[1]")
        ));
        pausaDramatica(1500);
        btnEditar.click();
        pausaDramatica(1000);

        WebElement inputPreco = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='preco']")));
        inputPreco.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        inputPreco.sendKeys("199.99");
        pausaDramatica(1000);

        driver.findElement(By.xpath("//button[contains(text(), 'Atualizar Livro')]")).click();

        Alert alertaEdicao = wait.until(ExpectedConditions.alertIsPresent());
        pausaDramatica(1000);
        alertaEdicao.accept();
        pausaDramatica(1500);

        WebElement btnExcluir = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//button[text()='X'])[2]")
        ));
        btnExcluir.click();
        pausaDramatica(1000);

        Alert alertaExclusao = wait.until(ExpectedConditions.alertIsPresent());
        alertaExclusao.accept();
        pausaDramatica(1500);

        WebElement btnAprovar = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Aprovar Pagamento')]")
        ));

        ((ChromeDriver) driver).executeScript("arguments[0].scrollIntoView(true);", btnAprovar);
        pausaDramatica(1500);

        btnAprovar.click();

        pausaDramatica(3000);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}