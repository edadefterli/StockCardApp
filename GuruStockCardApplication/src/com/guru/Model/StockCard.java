package com.guru.Model;

import com.guru.Controller.DBConnector;
import com.guru.Controller.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class StockCard {
    private int id;
    private String stock_code;
    private String stock_name;
    private int stock_type;
    private String unit;
    private String barcode;
    private double vat_type;
    private String explanation;
    private Date creation_date;

    public StockCard(){

    }

    public StockCard(String stock_code, String stock_name, int stock_type, String unit, String barcode, double vat_type, String explanation) {
        this.stock_code = stock_code;
        this.stock_name = stock_name;
        this.stock_type = stock_type;
        this.unit = unit;
        this.barcode = barcode;
        this.vat_type = vat_type;
        this.explanation = explanation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStock_code() {
        return stock_code;
    }

    public void setStock_code(String stock_code) {
        this.stock_code = stock_code;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public int getStock_type() {
        return stock_type;
    }

    public void setStock_type(int stock_type) {
        this.stock_type = stock_type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public double getVat_type() {
        return vat_type;
    }

    public void setVat_type(double vat_type) {
        this.vat_type = vat_type;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public static ArrayList<StockCard> getList(){
        ArrayList<StockCard> stockCardList = new ArrayList<>();
        String query = "Select * from stock_cards";
        StockCard obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new StockCard();
                obj.setId(rs.getInt("id"));
                obj.setStock_code(rs.getString("stock_code"));
                obj.setStock_name(rs.getString("stock_name"));
                obj.setStock_type(rs.getInt("stock_type"));
                obj.setUnit(rs.getString("unit"));
                obj.setBarcode(rs.getString("barcode"));
                obj.setVat_type(rs.getDouble("vat_type"));
                obj.setExplanation(rs.getString("explanation"));
                obj.setCreation_date(rs.getDate("creation_date"));
                stockCardList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return stockCardList;
    }

    public static ArrayList<StockCard> getFetch(String stockCode){
        ArrayList<StockCard> stockCardList = new ArrayList<>();
        String query = "SELECT * FROM guru.stock_cards where stock_code = ?";
        StockCard obj;
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,stockCode);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new StockCard();
                obj.setId(rs.getInt("id"));
                obj.setStock_code(rs.getString("stock_code"));
                obj.setStock_name(rs.getString("stock_name"));
                obj.setStock_type(rs.getInt("stock_type"));
                obj.setUnit(rs.getString("unit"));
                obj.setBarcode(rs.getString("barcode"));
                obj.setVat_type(rs.getDouble("vat_type"));
                obj.setExplanation(rs.getString("explanation"));
                obj.setCreation_date(rs.getDate("creation_date"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stockCardList;
    }

    public static boolean add(StockCard stockCard){
        String query = "INSERT INTO guru.stock_cards(stock_code,stock_name,stock_type,unit,barcode,vat_type,explanation) values (?,?,?,?,?,?,?);";
        StockCard findCard = StockCard.getFetchByCode(stockCard.getStock_code());
        int response;
        if(findCard != null){
            Helper.showMessage("Bu Stok Kodu daha önceden eklenmiş. Lütfen yeni bir değer giriniz.");
            return false;
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,stockCard.getStock_code());
            pr.setString(2,stockCard.getStock_name());
            pr.setInt(3,stockCard.getStock_type());
            pr.setString(4,stockCard.getUnit());
            pr.setString(5,stockCard.getBarcode());
            pr.setDouble(6,stockCard.getVat_type());
            pr.setString(7,stockCard.getExplanation());
            response = pr.executeUpdate();
            if(response== -1){
                Helper.showMessage("error");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return response != -1;

    }

    public static StockCard getFetchByCode(String stockCode){
        StockCard obj = null;
        String query = "SELECT * FROM guru.stock_cards where stock_code = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,stockCode);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new StockCard();
                obj.setId(rs.getInt("id"));
                obj.setStock_code(rs.getString("stock_code"));
                obj.setStock_name(rs.getString("stock_name"));
                obj.setStock_type(rs.getInt("stock_type"));
                obj.setUnit(rs.getString("unit"));
                obj.setBarcode(rs.getString("barcode"));
                obj.setVat_type(rs.getDouble("vat_type"));
                obj.setExplanation(rs.getString("explanation"));
                obj.setCreation_date(rs.getDate("creation_date"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static boolean delete(String stock_code) {
        String query = "DELETE FROM GURU.STOCK_CARDS WHERE stock_code = ?";
        int response ;
        StockCard findCard = StockCard.getFetchByCode(stock_code);
        if(findCard == null){
            Helper.showMessage("Girilen Stok Kartı bulunamamıştır. Lütfen geçerli bir Stok Kartı giriniz.");
            return false;
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,stock_code);
            response = pr.executeUpdate();
            if(response == -1){
                Helper.showMessage("error");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return response != -1;
    }


    public static boolean update(int stockId,String stock_code,String stock_name,int stock_type,String unit,String barcode,Double
                                 vat_type,String explanation) {
        String query = "UPDATE guru.stock_cards set stock_name = ?,stock_type = ?, unit = ?, barcode = ?,vat_type = ?,explanation = ? where id = ?";
        int response;
        StockCard findCard = StockCard.getFetchByCode(stock_code);
        if(findCard != null && findCard.getId() != stockId){
            Helper.showMessage("Bu Stok Kodu daha önceden eklenmiş. Lütfen yeni bir değer giriniz.");
            return false;
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,stock_name);
            pr.setInt(2,stock_type);
            pr.setString(3,unit);
            pr.setString(4,barcode);
            pr.setDouble(5,vat_type);
            pr.setString(6,explanation);
            pr.setInt(7,stockId);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static boolean copy(String oldStockCode, String newStockCode){
        StockCard newFindCard = StockCard.getFetchByCode(newStockCode);
        StockCard oldFindCard = StockCard.getFetchByCode(oldStockCode);
        StockCard newCard = new StockCard();
        if(newFindCard != null){
            Helper.showMessage("Yeni girilen Stok Kodu daha önceden eklenmiş. Lütfen yeni bir değer giriniz.");
            return false;
        }else if(oldFindCard == null){
            Helper.showMessage("Kopyalanacak Stok Kodu bulunamadı.");
            return false;
        }
        else{
            StockCard newObj = StockCard.getFetchByCode(oldStockCode);
            newCard.setStock_code(newStockCode);
            newCard.setStock_name(newObj.getStock_name());
            newCard.setStock_type(newObj.getStock_type());
            newCard.setUnit(newObj.getUnit());
            newCard.setBarcode(newObj.getBarcode());
            newCard.setVat_type(newObj.getVat_type());
            newCard.setExplanation(newObj.getExplanation());
        }
        return add(newCard);
    }

}
