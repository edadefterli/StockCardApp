package com.guru.View;

import com.guru.Controller.Config;
import com.guru.Controller.Helper;
import com.guru.Model.StockCard;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

public class ListGUI extends JFrame{
    private JPanel wrapper;
    private JTabbedPane tabbedPane1;
    private JPanel pnl_stokCards;
    private JScrollPane scrl_stokCards;
    private JTable tbl_stokCards;
    private DefaultTableModel mdl_stockCards;
    private Object[] row_stockCards;
    private final StockCard stockCard;

    public ListGUI(StockCard stockCard){
        this.stockCard = stockCard;
        add(wrapper);
        setSize(1000,500);
        int x = Helper.screenCenterPoint("x",getSize());
        int y = Helper.screenCenterPoint("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        mdl_stockCards = new DefaultTableModel();
        Object[] col_stock_list = {"ID","Stok Kodu","Stok Adı","Stok Tipi","Birimi","Barkodu","KDV Tipi",
                "Açıklama","Oluşturma Zamanı"};
        mdl_stockCards.setColumnIdentifiers(col_stock_list);

        row_stockCards = new Object[col_stock_list.length];
        loadStockCardModel();

        tbl_stokCards.setModel(mdl_stockCards);
        tbl_stokCards.getTableHeader().setReorderingAllowed(false);

        tbl_stokCards.getModel().addTableModelListener(e1 -> {
            if(e1.getType() == TableModelEvent.UPDATE){
                if(e1.getColumn() == 0 || e1.getColumn() == 1 || e1.getColumn() == 8){
                    Helper.showMessage("Bu kolon güncellenemez!");
                }else{
                    int stockId = Integer.parseInt(tbl_stokCards.getValueAt(tbl_stokCards.getSelectedRow(),0).toString());
                    String stockCode = tbl_stokCards.getValueAt(tbl_stokCards.getSelectedRow(),1).toString();
                    String stockName = tbl_stokCards.getValueAt(tbl_stokCards.getSelectedRow(),2).toString();
                    Integer stockType = Integer.parseInt(tbl_stokCards.getValueAt(tbl_stokCards.getSelectedRow(),3).toString());
                    String unit = tbl_stokCards.getValueAt(tbl_stokCards.getSelectedRow(),4).toString();
                    String barcode = tbl_stokCards.getValueAt(tbl_stokCards.getSelectedRow(),5).toString();
                    Double vatType = Double.valueOf(tbl_stokCards.getValueAt(tbl_stokCards.getSelectedRow(),6).toString());
                    String explanation = tbl_stokCards.getValueAt(tbl_stokCards.getSelectedRow(),7).toString();
                    //String creationDate = tbl_stokCards.getValueAt(tbl_stokCards.getSelectedRow(),8).toString();

                    if(StockCard.update(stockId,stockCode,stockName,stockType,unit,barcode,vatType,explanation)){
                        Helper.showMessage("done");
                    }else{
                        Helper.showMessage("error");
                    }
                }
                loadStockCardModel();

            }
        });

    }

    public void loadStockCardModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_stokCards.getModel();
        clearModel.setRowCount(0);

        for(StockCard obj : StockCard.getList()){
            row_stockCards[0] = obj.getId();
            row_stockCards[1] = obj.getStock_code();
            row_stockCards[2] = obj.getStock_name();
            row_stockCards[3] = obj.getStock_type();
            row_stockCards[4] = obj.getUnit();
            row_stockCards[5] = obj.getBarcode();
            row_stockCards[6] = obj.getVat_type();
            row_stockCards[7] = obj.getExplanation();
            row_stockCards[8] = obj.getCreation_date();
            mdl_stockCards.addRow(row_stockCards);
        }
    }

    public static void main(String[] args){
        Helper.setLayout();
        StockCard sc = new StockCard();
        ListGUI listGUI = new ListGUI(sc);
    }
}
