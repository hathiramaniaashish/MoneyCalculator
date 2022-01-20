package view;

import controller.Controller;
import controller.persistence.archive.ArchiveCurrencyLoader;
import controller.persistence.webservice.WebServiceExchangeRateLoader;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Currency;
import model.ExchangeRate;
import model.Money;

public class FrameApp extends JFrame implements Dialog, Display {
    // moneyDialogPanel
    private JLabel fromLabel = new JLabel("FROM:");
    private JLabel toLabel = new JLabel("TO:");
    private JLabel amountLabel = new JLabel("AMOUNT:");
    private JTextField amountField = new JTextField();
    private JComboBox fromBox = new JComboBox();
    private JComboBox toBox = new JComboBox();
    private JButton exitButton = new JButton("EXIT");
    private JButton calculateButton = new JButton("CALCULATE");
    
    // moneyDisplayPanel
    private JLabel displayLabel = new JLabel();
    
    public FrameApp() {
        setTitle("Money Calculator");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        fillComboBox(fromBox);
        fillComboBox(toBox);
        add(moneyDialogPanel(), BorderLayout.NORTH);
        add(moneyDisplayPanel(), BorderLayout.SOUTH);
        setVisible(true);
    }
    
    public void fillComboBox(JComboBox comboBox) {
        ArchiveCurrencyLoader loader = new ArchiveCurrencyLoader("currencies.txt");
        for (Currency currency : loader.loadCurrencies()) {
            comboBox.addItem(currency);
        }
    }
    
    public JPanel moneyDialogPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 20, 20));
        panel.add(fromLabel);
        panel.add(fromBox);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(toLabel);
        panel.add(toBox);
        exitButton.addActionListener(getExitButtonActionListener());
        panel.add(exitButton);
        calculateButton.addActionListener(getCalculateButtonActionListener());
        panel.add(calculateButton);
        return panel;
    }
    
    public ActionListener getExitButtonActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
    }
    
    public ActionListener getCalculateButtonActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller controller = new Controller();
                Money fromMoney = getMoney();
                Currency from = getCurrencyFrom();
                Currency to = getCurrencyTo();
                ExchangeRate exchangeRate = new WebServiceExchangeRateLoader().loadExchangeRate(from, to);
                Money toMoney = new Money(controller.getMoneyConverted(fromMoney, exchangeRate), to);
                display(toMoney);
            }
        };
    }
    
    public JPanel moneyDisplayPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 100));
        panel.add(displayLabel);
        return panel;
    }

    @Override
    public Money getMoney() {
        double amount = -1;
        if (amountField.getText() != null) {
            try {
                amount = Double.parseDouble(amountField.getText());
            } catch (NumberFormatException ex) {}
        }
        return new Money(amount, getCurrencyFrom());
    }

    @Override
    public Currency getCurrencyFrom() {
        return (Currency) fromBox.getSelectedItem();
    }

    @Override
    public Currency getCurrencyTo() {
        return (Currency) toBox.getSelectedItem();
    }

    @Override
    public void display(Money money) {
        if (money.getAmount() < 0) {
            displayLabel.setText("ERROR, ENTER A VALID AMOUNT");
        } else {
            displayLabel.setText(money.toString());
        }
    }
    
}
