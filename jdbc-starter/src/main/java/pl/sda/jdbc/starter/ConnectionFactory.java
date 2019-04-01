package pl.sda.jdbc.starter;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);
    private DataSource dataSource;


    public ConnectionFactory(String path) throws SQLException {
        Properties properties = getDataBaseProperties(path);
        dataSource = createDatasource(properties);

    }
    public ConnectionFactory() throws SQLException {

        this("/database.properties");

    }
    private DataSource createDatasource(Properties properties) throws SQLException {

        MysqlDataSource dataSource;
        String serverName = properties.getProperty("pl.sda.jdbc.db.server");
        String databaseName = properties.getProperty("pl.sda.jdbc.db.name");
        String userName = properties.getProperty("pl.sda.jdbc.db.user");
        String password = properties.getProperty("pl.sda.jdbc.db.password");
        String port = properties.getProperty("pl.sda.jdbc.db.port");


            dataSource = new MysqlDataSource();
            dataSource.setServerName(serverName);
            dataSource.setDatabaseName(databaseName);
            dataSource.setUser(userName);
            dataSource.setPassword(password);
            dataSource.setPort(Integer.parseInt(port));
            dataSource.setServerTimezone("Europe/Warsaw");
            dataSource.setUseSSL(false);
            dataSource.setCharacterEncoding("UTF-8");

            return dataSource;
        }




    private Properties getDataBaseProperties(String filename) {
        Properties properties = new Properties();
        try {
            /**
             * Pobieramy zawartość pliku za pomocą classloadera, plik musi znajdować się w katalogu ustawionym w CLASSPATH
             */
            InputStream propertiesStream = ConnectionFactory.class.getResourceAsStream(filename);
            if(propertiesStream == null) {
                throw new IllegalArgumentException("Can't find file: " + filename);
            }
            /**
             * Pobieramy dane z pliku i umieszczamy w obiekcie klasy Properties
             */
            properties.load(propertiesStream);
        } catch (IOException e) {
            logger.error("Error during fetching properties for database", e);
            return null;
        }

        return properties;
    }

    public Connection getConnection() throws SQLException {
        if(dataSource == null){
            throw new IllegalStateException("Data souce must be created");
        }
        return dataSource.getConnection();


    }

    public static void main(String[] args) {

        try {
            ConnectionFactory myNewConnecntion = new ConnectionFactory("/remote-database.properties");
            try (Connection newConnection = myNewConnecntion.getConnection()){
                logger.info("Connection = " + myNewConnecntion);
                logger.info("Database name = " + newConnection.getCatalog());

            }
        } catch (SQLException e) {
            logger.error("Exception durning connection");


        }



    }
}