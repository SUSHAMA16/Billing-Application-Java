package com.example;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

class Product {
    
    private String id;
    private String pname;
    private int qty;
    private double price;
    private double totalPrice;

    
    Product(String id, String pname, int qty, double price, double totalPrice) {
        this.id = id;
        this.pname = pname;
        this.qty = qty;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    
    public String getId() {
        return id;
    }

    public String getPname() {
        return pname;
    }

    public int getQty() {
        return qty;
    }

    public double getPrice() {
        return price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    
    public static void displayFormat() {
        System.out.format("-----------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("\nProduct ID \t\tName\t\tQuantity\t\tRate \t\t\t\tTotal Price\n");
        System.out.format("-----------------------------------------------------------------------------------------------------------------------------------\n");
    }

    
    public void display() {
        System.out.format("   %-9s             %-9s      %5d               %9.2f                       %14.2f\n", id, pname, qty, price, totalPrice);
    }
}

public class ShoppingBill {
    public static void main(String args[]) {
        
        String id = null;
        String productName = null;
        int quantity = 0;
        double price = 0.0;
        double totalPrice = 0.0;
        double overAllPrice = 0.0;
        double cgst, sgst, subtotal = 0.0, discount = 0.0;
        char choice = '\0';
        System.out.println("\t\t\t\t-----------------Invoice-----------------");
        System.out.println("\t\t\t\t\t " + "  " + "Mcure Pharmaceuticals");
        System.out.println("\t\t\t\t\t    3/18 Kothrud, Pune");
        System.out.println("\t\t\t\t\t" + "    " + "Opposite Metro Walk");
        System.out.println("----------------------------------------------------------------------------------------------------------------------");
        System.out.println("GSTIN: SDSFEG2545" + "\t\t\t\t\t\t\tContact: (+91) 154154651534");
       
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        
        System.out.println("Date: " + formatter.format(date) + "  " + days[calendar.get(Calendar.DAY_OF_WEEK) - 1] + "\t\t\t\t\t\t (+91) 9998887770");
        System.out.println("----------------------------------------------------------------------------------------------------------------------");
        Scanner scan = new Scanner(System.in);
        System.out.println();
        System.out.print("Enter Customer Name: ");
        String customername = scan.nextLine();
        System.out.println();
       
        List<Product> product = new ArrayList<Product>();
        do {
           
            System.out.println("Enter the product details: ");
            System.out.print("Product ID: ");
            id = scan.nextLine();
            System.out.print("Product Name: ");
            productName = scan.nextLine();
            System.out.print("Quantity: ");
            quantity = scan.nextInt();
            System.out.print("Price (per unit): ");
            price = scan.nextDouble();
            
            totalPrice = price * quantity;
            
            overAllPrice = overAllPrice + totalPrice;
            
            product.add(new Product(id, productName, quantity, price, totalPrice));
            
            System.out.print("Want to add more items? (y or n): ");
            
            choice = scan.next().charAt(0);
            
            scan.nextLine();
            System.out.println();
        } while (choice == 'y' || choice == 'Y');
        
        Product.displayFormat();
        for (Product p : product) {
            p.display();
        }
       
        System.out.println("\n\t\t\t\t\t\t\t\t\t\t\t      Total Amount (Rs.) " + overAllPrice);
        
        discount = overAllPrice * 2 / 100;
        System.out.println("\n\t\t\t\t\t\t\t\t\t\t\t      Discount (Rs.) " + discount);
        
        subtotal = overAllPrice - discount;
        System.out.println("\n\t\t\t\t\t\t\t\t\t\t\t      Subtotal " + subtotal);
        
        sgst = overAllPrice * 12 / 100;
        System.out.println("\n\t\t\t\t\t\t\t\t\t\t\t      SGST (%) " + sgst);
        cgst = overAllPrice * 12 / 100;
        System.out.println("\n\t\t\t\t\t\t\t\t\t\t\t      CGST (%) " + cgst);
        
        System.out.println("\n\t\t\t\t\t\t\t\t\t\t\t      Invoice Total " + (subtotal + cgst + sgst));
        System.out.println("\t\t\t\t------------Thank You for Shopping!!--------------");
        System.out.println("\t\t\t\t                  Visit Again");
        System.out.println("Do you want to print the invoice? (y or n)");
        choice = scan.next().charAt(0);
        if (choice == 'y' || choice == 'Y') {
            try {
                Document document = new Document();
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("invoice.pdf"));
                document.open();
                document.add(new Paragraph("Mcure Pharmaceuticals Invoice"));
                document.add(new Paragraph("Customer Name: " + customername));
                document.add(new Paragraph("Date: " + formatter.format(date)));
                document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------"));
                Product.displayFormat();
                for (Product p : product) {
                    document.add(new Paragraph("   " + p.getId() + "             " + p.getPname() + "      " + p.getQty() + "               " + p.getPrice() + "                       " + p.getTotalPrice()));
                }
                document.add(new Paragraph("\n\t\t\t\t\t\t\t\t\t\t\t      Total Amount (Rs.) " + overAllPrice));
                document.add(new Paragraph("\n\t\t\t\t\t\t\t\t\t\t\t      Discount (Rs.) " + discount));
                document.add(new Paragraph("\n\t\t\t\t\t\t\t\t\t\t\t      Subtotal " + subtotal));
                document.add(new Paragraph("\n\t\t\t\t\t\t\t\t\t\t\t      SGST (%) " + sgst));
                document.add(new Paragraph("\n\t\t\t\t\t\t\t\t\t\t\t      CGST (%) " + cgst));
                document.add(new Paragraph("\n\t\t\t\t\t\t\t\t\t\t\t      Invoice Total " + (subtotal + cgst + sgst)));
                document.add(new Paragraph("\t\t\t\t------------Thank You for Shopping!!--------------"));
                document.add(new Paragraph("\t\t\t\t                  Visit Again"));
                document.close();
                System.out.println("Invoice has been created as invoice.pdf");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       
        scan.close();
    }
}