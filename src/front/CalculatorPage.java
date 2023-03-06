package front;

import back.Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class CalculatorPage extends JFrame implements ActionListener {

    private JButton[] buttonsNumbers = new JButton[9];
    private JPanel numberPanel = new JPanel();

    private JPanel operationPanel = new JPanel();

    private JButton add = new JButton("+");
    private JButton sub = new JButton("-");
    private JButton mul = new JButton("*");
    private JButton div = new JButton("/");

    private JPanel keyboard = new JPanel();

    private JTextField result = new JTextField("0");

    private StringBuilder expression = new StringBuilder("");

    private JButton equals = new JButton("=");

    private JButton clear = new JButton("C");
    private JButton zero = new JButton("0");



    public CalculatorPage() {
        // add number buttons
        for(int i = 1; i <= this.buttonsNumbers.length; i++){
            this.buttonsNumbers[i - 1] = new JButton(Integer.toString(i));
        }

        // numberPanel
        this.numberPanel.setLayout(new GridLayout(4, 3));

        // operationpanel layout
        this.operationPanel.setLayout(new GridLayout(4, 1));

        // keyboard panel
        this.keyboard.setLayout(new GridLayout(1, 2));

        // set result styles
        this.result.setFont(new Font("SansSerif", Font.BOLD, 24));
        this.result.setHorizontalAlignment(JTextField.CENTER);
        this.result.setEditable(false);


        for(JButton b : this.buttonsNumbers){
            b.addActionListener(this);
            this.numberPanel.add(b);
        }

        // add zero clear and equals to number panel
        this.numberPanel.add(this.clear);
        this.numberPanel.add(this.zero);
        this.numberPanel.add(this.equals);

        // add event lieners
        this.clear.addActionListener(this);
        this.zero.addActionListener(this);
        this.equals.addActionListener(this);

        // add operations to operation panel
        this.operationPanel.add(this.add);
        this.operationPanel.add(this.sub);
        this.operationPanel.add(this.mul);
        this.operationPanel.add(this.div);

        // add action listeners to operations
        this.add.addActionListener(this);
        this.sub.addActionListener(this);
        this.mul.addActionListener(this);
        this.div.addActionListener(this);



        // add panels to frame
        this.keyboard.add(this.numberPanel);
        this.keyboard.add(this.operationPanel);

        // add screen
        this.add(this.result);

        // add keyboard
        this.add(this.keyboard);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(2, 1));
        this.pack();
        this.setVisible(true);

    }


    private double evalExpr(StringBuilder str_){

        // Split the expression into individual operands and operators
        String[] tokens = str_.toString().split("(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)");
        List<String> operands = new ArrayList<>();
        List<String> operators = new ArrayList<>();
        for (String token : tokens) {
            if (token.matches("[+\\-*/]")) {
                operators.add(token);
            } else {
                operands.add(token);
            }
        }


        // Evaluate the expression using the arithmetic operations functions
        double result = Double.parseDouble(operands.get(0));
        for (int i = 0; i < operators.size(); i++) {
            String operator = operators.get(i);
            double operand = Double.parseDouble(operands.get(i + 1));
            switch (operator) {
                case "+":
                    result = Calculator.Add(result, operand);
                    break;
                case "-":
                    result = Calculator.Sub(result, operand);
                    break;
                case "*":
                    result = Calculator.Mul(result, operand);
                    break;
                case "/":
                    result = Calculator.Div(result, operand);
                    break;
            }
        }

        return result;

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();
        String stringExpression = this.expression.toString();
        char lastChar = stringExpression.length() > 0 ? stringExpression.charAt(stringExpression.length() - 1) : 'e';
        boolean operationIsNotPossible = (lastChar == '+') || (lastChar == '-') || (lastChar == '*') || (lastChar == '/') || (lastChar == 'e');

        if (this.add.equals(source) && !operationIsNotPossible) {
            this.expression.append("+");
        } else if (this.sub.equals(source) && !operationIsNotPossible) {
            this.expression.append("-");
        } else if (this.mul.equals(source) && !operationIsNotPossible) {
            this.expression.append("*");
        } else if (this.div.equals(source) && !operationIsNotPossible) {
            this.expression.append("/");
        } else if(source == this.equals){
            int res =  (int) evalExpr(this.expression);
            this.expression = new StringBuilder(Integer.toString(res));
        } else if (source == this.zero) {
            this.expression.append("0");
        } else if (source == this.clear) {
            this.expression = new StringBuilder("");
        }

        for(int i = 0; i < this.buttonsNumbers.length; i++){
            if (source == this.buttonsNumbers[i]){
                this.expression.append(i + 1);
            }
        }


        this.result.setText(this.expression.toString());
    }
}
