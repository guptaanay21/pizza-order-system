import java.util.Scanner;

public class Pizza {

    private int price;
    private boolean veg;
    private int extraCheesePrice = 100;
    private int extraToppingPrice = 150;
    private int packagingPrice = 20;
    private int membershipCardPrice = 500;

    private int basePizzaPrice;
    private boolean isExtraCheeseAdded = false;
    private boolean isExtraToppingsAdded = false;
    private boolean isOptedorFTakeAwayAdded = false;
    private boolean hasMembershipCard = false;

    private double taxRate = 0.1; // 10% tax
    private double discountRate = 0.05; // 5% discount
    private double birthdayDiscountRate = 0.25; // 25% discount for birthdays
    private double membershipDiscountRate = 0.1; // 10% discount for membership

    // Enum for Payment Methods
    public enum PaymentMethod {
        DEBIT_CARD,
        CREDIT_CARD,
        WALLET,
        CASH
    }

    private PaymentMethod paymentMethod; // Field to store chosen payment method

    public Pizza(boolean veg) {
        this.veg = veg;
        if (this.veg) {
            this.price = 300;
        } else {
            this.price = 400;
        }
        basePizzaPrice = this.price;
    }

    public void addExtraCheese() {
        isExtraCheeseAdded = true;
        this.price += extraCheesePrice;
    }

    public void addExtraTopping() {
        isExtraToppingsAdded = true;
        this.price += extraToppingPrice;
    }

    public void takeAway() {
        isOptedorFTakeAwayAdded = true;
        this.price += packagingPrice;
    }

    public void addMembershipCard() {
        hasMembershipCard = true;
        System.out.println("Membership card purchased for: " + membershipCardPrice);
    }

    // Method to choose payment method
    public void choosePaymentMethod(PaymentMethod method) {
        this.paymentMethod = method;
        System.out.println("Payment Method chosen: " + method);
    }

    // Method to calculate the total tax
    private double calculateTax() {
        return this.price * taxRate;
    }

    // Method to calculate the discount
    private double calculateDiscount(boolean isBirthday) {
        double discount = 0;
        if (isBirthday) {
            discount = this.price * birthdayDiscountRate;
        } else if (hasMembershipCard) {
            discount = this.price * membershipDiscountRate;
        } else {
            discount = this.price * discountRate;
        }
        return discount;
    }

    // Method to generate invoice including tax, discount, and payment method
    public void generateInvoice(boolean isBirthday) {
        double discount = calculateDiscount(isBirthday);
        double tax = calculateTax();
        double totalAfterDiscount = this.price - discount;
        double finalAmount = totalAfterDiscount + tax;

        String invoice = "Invoice Details:\n";
        invoice += "Base Pizza (" + (veg ? "Vegetarian" : "Non-Vegetarian") + "): " + basePizzaPrice + "\n";
        if (isExtraCheeseAdded) {
            invoice += "Extra Cheese Added: " + extraCheesePrice + "\n";
        }
        if (isExtraToppingsAdded) {
            invoice += "Extra Topping Added: " + extraToppingPrice + "\n";
        }
        if (isOptedorFTakeAwayAdded) {
            invoice += "Take Away: " + packagingPrice + "\n";
        }
        if (hasMembershipCard) {
            invoice += "Membership Card Discount (10%): -" + (this.price * membershipDiscountRate) + "\n";
        }
        invoice += "Subtotal: " + this.price + "\n";
        invoice += "Discount (" + (isBirthday ? "25% Birthday Discount" : (hasMembershipCard ? "10% Membership Discount" : "5% Regular Discount")) + "): -" + discount + "\n";
        invoice += "Tax (10%): +" + tax + "\n";
        invoice += "Total Amount Due: " + finalAmount + "\n";
        if (paymentMethod != null) {
            invoice += "Payment Method: " + paymentMethod + "\n";
        } else {
            invoice += "Payment Method: Not selected\n";
        }

        System.out.println(invoice);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Pizza World");
        System.out.println("Please choose the type of pizza:");
        System.out.println("1. Vegetarian");
        System.out.println("2. Non-Vegetarian");
        int pizzaTypeChoice = scanner.nextInt();
        boolean isVeg = (pizzaTypeChoice == 1);

        Pizza pizza = new Pizza(isVeg);

        System.out.println("Do you want extra cheese? (yes/no)");
        String cheeseChoice = scanner.next();
        if (cheeseChoice.equalsIgnoreCase("yes")) {
            pizza.addExtraCheese();
        }

        System.out.println("Do you want extra toppings? (yes/no)");
        String toppingChoice = scanner.next();
        if (toppingChoice.equalsIgnoreCase("yes")) {
            pizza.addExtraTopping();
        }

        System.out.println("Do you want to take away? (yes/no)");
        String takeAwayChoice = scanner.next();
        if (takeAwayChoice.equalsIgnoreCase("yes")) {
            pizza.takeAway();
        }

        System.out.println("Do you have a membership card? (yes/no)");
        String membershipChoice = scanner.next();
        boolean hasMembershipCard = membershipChoice.equalsIgnoreCase("yes");
        if (hasMembershipCard) {
            pizza.addMembershipCard();
        }

        System.out.println("Is it your birthday? (yes/no)");
        String birthdayChoice = scanner.next();
        boolean isBirthday = birthdayChoice.equalsIgnoreCase("yes");

        System.out.println("Choose your payment method:");
        System.out.println("1. DEBIT_CARD");
        System.out.println("2. CREDIT_CARD");
        System.out.println("3. WALLET");
        System.out.println("4. CASH");
        int paymentMethodChoice = scanner.nextInt();
        Pizza.PaymentMethod paymentMethod = Pizza.PaymentMethod.values()[paymentMethodChoice - 1];
        pizza.choosePaymentMethod(paymentMethod);

        pizza.generateInvoice(isBirthday);
        scanner.close();
    }
}
