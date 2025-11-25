package com.example.cadastro_clientes.selenium;

import com.example.cadastro_clientes.model.Cliente;
import com.example.cadastro_clientes.repository.ClienteRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClienteSeleniumIT {

    private static WebDriver driver;

    @Autowired
    private ClienteRepository repository;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @AfterAll
    static void tearDownClass() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeEach
    void limparBanco() {
        repository.deleteAll();
    }

    private void abrirAplicacao() throws InterruptedException {
        driver.get("http://localhost:8081/index.html");
        // pequeno tempo para o JavaScript carregar a tabela
        Thread.sleep(800);
    }

    @Test
    @Order(1)
    void testeInsert() throws InterruptedException {
        abrirAplicacao();

        driver.findElement(By.id("nome")).sendKeys("Cliente Selenium");
        driver.findElement(By.id("email")).sendKeys("selenium@test.com");
        driver.findElement(By.id("telefone")).sendKeys("123456");
        driver.findElement(By.id("cidade")).sendKeys("Cidade X");

        driver.findElement(By.id("btnSalvar")).click();

        Thread.sleep(800);

        // Feedback visual
        WebElement msg = driver.findElement(By.id("mensagem"));
        assertTrue(msg.getText().toLowerCase().contains("sucesso"));

        // Verifica persistência no banco
        List<Cliente> clientes = repository.findAll();
        assertEquals(1, clientes.size());
        assertEquals("Cliente Selenium", clientes.get(0).getNome());
    }

    @Test
    @Order(2)
    void testeUpdate() throws InterruptedException {
        // Inserir um cliente diretamente no banco
        Cliente cliente = new Cliente("Antigo", "antigo@test.com", "000", "Cidade A");
        cliente = repository.save(cliente);

        abrirAplicacao();

        // Botão Editar da primeira linha
        WebElement btnEditar = driver.findElement(
                By.xpath("//table[@id='tabelaClientes']/tbody/tr[1]/td[6]/button[1]")
        );
        btnEditar.click();

        Thread.sleep(500);

        WebElement campoNome = driver.findElement(By.id("nome"));
        campoNome.clear();
        campoNome.sendKeys("Nome Atualizado");

        driver.findElement(By.id("btnSalvar")).click();

        Thread.sleep(800);

        WebElement msg = driver.findElement(By.id("mensagem"));
        assertTrue(msg.getText().toLowerCase().contains("sucesso"));

        Cliente atualizado = repository.findAll().get(0);
        assertEquals("Nome Atualizado", atualizado.getNome());
    }

    @Test
    @Order(3)
    void testeDelete() throws InterruptedException {
        // Inserir cliente que será deletado
        Cliente cliente = new Cliente("Para Deletar", "del@test.com", "999", "Cidade D");
        repository.save(cliente);

        abrirAplicacao();

        WebElement btnExcluir = driver.findElement(
                By.xpath("//table[@id='tabelaClientes']/tbody/tr[1]/td[6]/button[2]")
        );
        btnExcluir.click();

        // Confirma o alert do navegador
        Alert alert = driver.switchTo().alert();
        alert.accept();

        Thread.sleep(800);

        List<Cliente> clientes = repository.findAll();
        assertTrue(clientes.isEmpty());
    }
}
