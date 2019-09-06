package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import ranglista.RangLista;
import ranglista.Igrac;

public class DatabaseConnection {

    RangLista rangLista = new RangLista();
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public LinkedList<Igrac> ucitajRangListu() {
        String upit = "select * from ranglista";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kviz", "root", "");
                Statement stat = conn.createStatement();
                ResultSet rs = stat.executeQuery(upit);) {
            while (rs.next()) {
                String ime = rs.getString(1);
                int brojPoena = rs.getInt(2);
                Igrac i = new Igrac(ime, brojPoena);
                rangLista.sacuvajIgraca(i);
            }

        } catch (SQLException ex) {

            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rangLista.vratiIgrace();
    }

    public int ubaciIgraca(String ime, int brojPoena) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kviz", "root", "");
            connection.setAutoCommit(false);
            String query = "INSERT INTO RANGLISTA(ime, brojpoena) VALUES(?,?) ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, ime);
            preparedStatement.setInt(2, brojPoena);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                connection.commit();
            } else {
                connection.rollback();
                System.out.println("rollback");
            }
            preparedStatement.close();
            connection.close();
            return row;
        } catch (SQLException e) {
            System.out.println("Greska kod konekcije");
            e.printStackTrace();
        }
        return 0;
    }
}
