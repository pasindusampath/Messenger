import java.util.Arrays;
import java.util.Scanner;

class home {

    private static Scanner input = new Scanner(System.in);

    static String username = "Thisanga";
    static String password = "1234";
    private static String[][] suppliers = new String[2][0];
    private static String[][] finale = new String[4][0];
    private static int[][] prices = new int[2][0];
    private static String[] category = new String[0];

    public static void clearConsole() {
        try {
            // Send the clear screen command to the console
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (Exception e) {
            // Handle any exceptions that may occur
            e.printStackTrace();
        }
    }

    public static void lineAnimation() {
        System.out.print("+");
        for (int i = 0; i < 49; i++) {
            try {
                Thread.sleep(5);
            } catch (Exception e) {
            }
            System.out.print("--");
        }
        //System.out.println("\b\b\b\b ");
        System.out.println("+");
    }

    public static void topicAdd(String title) {
        lineAnimation();
        System.out.printf("|%49s", title);
        System.out.printf("%49s|\n", " ");
        lineAnimation();
    }

    public static void login() {
        topicAdd("LOGIN PAGE");
        System.out.println();
        System.out.println();


        while (true) {
            System.out.print("User Name:");
            String u = input.next();
            if (u.equals(username)) {
                getPassword();
                break;
            } else {
                System.out.println("User name is invalid. please try again !");
                System.out.println();
            }
        }

    }

    public static void getPassword() {
        while (true) {
            System.out.print("Password:");
            String p = input.next();
            if (p.equals(password)) {
                break;
            } else {
                System.out.println("Password is incorrect. please try again !");
                System.out.println();
            }

        }
    }

    public static void mainOption() {
        topicAdd("WELCOME TO IJSE STOCK MANAGEMENT");
        System.out.println();
        System.out.println();
        System.out.println("[1] Change the Credentials \t\t\t\t[2] Supplier Management");
        System.out.println("[3] Stock Manage \t\t\t\t\t[4] Log Out");
        System.out.println("[5] Exit the System");
    }

    public static int getOption() {
        System.out.print("\n\n\n\n\n\nEnter An Option To Continue : ");
        return input.nextInt();
    }


    public static void option1() {
        topicAdd("CREDENTIAL MANAGEMENT");
        System.out.println();
        System.out.println();

        while (true) {
            System.out.print("Please enter the user Name to verify it's you :");
            String a = input.next();
            if (a.equals(username)) {
                System.out.println("Hey " + username + "!");
                System.out.println();
                changePassword();
                break;
            } else {
                System.out.println("Invalid user name. try again! :(");
                System.out.println();
            }
        }
    }

    public static void changePassword() {
        while (true) {
            System.out.print("Enter your current password:");
            String newpassword = input.next();
            if (newpassword.equals(password)) {
                System.out.print("Enter your new password :");
                String np = input.next();
                password = np;
                System.out.println();
                System.out.print("Password changed sucessfully !   Do you want to go the home page (Y/N) :");
                char ch = input.next().charAt(0);
                if ((ch == 'Y') || (ch == 'y')) {
                    clearConsole();
                    mainOption();
                }
                break;
            } else {
                System.out.println("Incorrect password.  try again ! :(");
                System.out.println();
            }

        }


    }


    public static void option2() {

        topicAdd("SUPPLIER MANAGE");
        System.out.println();
        System.out.println();
        System.out.println("[1] Add Supplier \t\t\t\t[2] Update Supplier");
        System.out.println("[3] Delete Supplier \t\t\t\t[4] Veiw Supplier");
        System.out.println("[5] Search supplier\t\t\t\t[6]Home Page");
        System.out.print("\n\n\n\n\n\nEnter An Option To Continue : ");
        int op2 = input.nextInt();
        if (op2 == 1) {
            clearConsole();
            topicAdd("ADD SUPPLIER");
            while (true) {
                System.out.print("Enter supplier Id :");
                String id = input.next();

                int index = searchArray(suppliers, id);

                if (index == -1) {
                    suppliers = incrementArray(suppliers);
                    suppliers[0][suppliers[0].length - 1] = id;


                    System.out.print("Enter the supplier  name:");
                    String name = input.next();

                    suppliers[1][suppliers[1].length - 1] = name;
                } else {
                    System.out.println("Already exists.pls try another");
                    System.out.println();
                    continue;
                }


                System.out.print("Successfully added! Do you want to add another supplier (Y/N)");
                char ch1 = input.next().charAt(0);
                System.out.println();
                if ((ch1 == 'N') || (ch1 == 'n')) {
                    clearConsole();
                    option2();

                }

            }
        }


        if (op2 == 2) {
            clearConsole();
            topicAdd("UPDATE SUPPLIER");

            while (true) {
                System.out.print("Supplier Id :");
                String id = input.next();

                int index = searchArray(suppliers, id);

                if (index == -1) {
                    System.out.println("Cant find supplier id .try again!");
                } else {
                    System.out.println("Supplier name :" + suppliers[1][index]);
                    System.out.println();
                    System.out.print("Enter the new supplier name :");
                    String name = input.next();
                    name = suppliers[1][index];

                }
                System.out.print("Updated sucessfully! Do you want to update another supplier(Y/N)");
                char ch2 = input.next().charAt(0);
                System.out.println();


                if ((ch2 == 'N') || (ch2 == 'n')) {
                    clearConsole();
                    option2();
                }

            }
        }


        if (op2 == 3) {
            clearConsole();
            topicAdd("DELETE SUPPLIER");

            while (true) {
                System.out.print("Supplier Id :");
                String id = input.next();

                int index = searchArray(suppliers, id);

                if (index == -1) {
                    System.out.println("Cant find supplier id .try again!");
                    System.out.println();
                } else {
                    suppliers = deleteArray(suppliers, index);
                    System.out.print("deleted sucessfully. Do you want to delete another supplier(Y/N)");
                    char ch3 = input.next().charAt(0);
                    System.out.println();


                    if ((ch3 == 'N') || (ch3 == 'n')) {
                        clearConsole();
                        option2();
                    }
                }
            }
        }

        if (op2 == 4) {
            clearConsole();
            topicAdd("VEIW SUPPLIER");

            while (true) {

                System.out.println("supplier[0].length" + " " + suppliers[0].length);
                System.out.println("supplier[1].length" + " " + suppliers[1].length);

                System.out.println("+----------------------+--------------------+");
                System.out.printf("|   %-16s   |  %-16s |\n", "Supplier Id", "Supplier Name");
                System.out.println("+----------------------+--------------------+");


                for (int i = 0; i < suppliers[0].length; i++) {

                    System.out.printf("|   %-16s   |  %-16s   |\n", suppliers[0][i], suppliers[1][i]);

                }
                System.out.println("+----------------------+--------------------+");

                System.out.print("Do you want to go to supplier management page(Y/N)");
                char ch4 = input.next().charAt(0);
                System.out.println();


                if ((ch4 == 'N') || (ch4 == 'n')) {
                    clearConsole();
                    option2();
                }
            }
        }

        if (op2 == 5) {
            clearConsole();
            topicAdd("SEARCH SUPPLIER");

            while (true) {
                System.out.print("Supplier Id :");
                String id = input.next();

                int index = searchArray(suppliers, id);

                if (index == -1) {
                    System.out.println("Cant find supplier id .try again!");
                    continue;
                } else {
                    System.out.println("Supplier name :" + suppliers[1][index]);
                    System.out.println();
                }
                System.out.print("added sucessfully! Do you want to add another finding? (Y/N)");
                char ch2 = input.next().charAt(0);
                System.out.println();


                if ((ch2 == 'N') || (ch2 == 'n')) {
                    clearConsole();
                    option2();
                }

            }
        }
        if (op2 == 5) {
            clearConsole();
            topicAdd("HOME PAGE");
            option1();
        }
    }

    public static String[] deleteArray2(String[] ar, int index) {
        String[] temp = new String[ar.length - 1];

        for (int i = 0, j = 0; i < ar.length; i++) {
            if (i == index) {
                continue;
            }
            temp[j++] = ar[i];
        }

        return temp;
    }

    public static String[][] deleteArray(String[][] ar, int index) {


        String[][] temp = new String[ar.length][ar[0].length - 1];

        for (int i = 0; i < ar.length; i++) {
            int destCol = 0;
            for (int j = 0; j < ar[i].length; j++) {
                if (j != index) {
                    temp[i][destCol] = ar[i][j];
                    destCol++;
                }
            }
        }

        return temp;
    }

    public static String[] incrementArray(String[] ar) {
        String[] temp = new String[ar.length + 1];
        for (int i = 0; i < ar.length; i++) {
            temp[i] = ar[i];

        }

        return temp;
    }

    public static String[][] incrementArray(String[][] ar) {
        String[][] temp = new String[ar.length][0];
        for (int i = 0; i < ar.length; i++) {
            temp[i] = incrementArray(ar[i]);

        }
        return temp;
    }


    public static int searchArray(String[][] arr, String data) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j].equals(data)) {
                    return j;
                }
            }

        }
        return -1;
    }

    public static int searchArray2(String[] ar, String data) {
        for (int i = 0; i < ar.length; i++) {
            if (ar[i].equals(data)) {
                return i;
            }

        }
        return -1;
    }


    public static void option3() {
        while (true) {
            topicAdd("STOCK MANAGE");
            System.out.println();
            System.out.println();
            System.out.println("[1] Manage Item Categories \t\t\t\t[2] Add Item");
            System.out.println();
            System.out.println("[3] Get Items Supplier Wise \t\t\t\t[4]Veiw Items");
            System.out.println();
            System.out.println("[5] Rank Items per Unit Price\t\t\t\t[6]Home Page");


            int op = getOption();
            clearConsole();

            if (op == 1) {
                int op1 = manageItem();
                clearConsole();


                if (op1 == 1) {
                    clearConsole();
                    topicAdd("ADD NEW ITEM CATEGORY");

                    while (true) {
                        System.out.print("Enter the new Item Category :");
                        String itemName = input.next();

                        int index = searchArray2(category, itemName);
                        if (index != -1) {
                            System.out.println("This category already exsists. pls try another one!");
                        } else {
                            category = incrementArray(category);
                            category[category.length - 1] = itemName;
                            System.out.println(Arrays.toString(category));

                            System.out.print("added sucessfully! Do you want to add another item category? (Y/N)");
                            char ch2 = input.next().charAt(0);
                            System.out.println();


                            if ((ch2 == 'Y') || (ch2 == 'y')) {

                                continue;
                            }
                            if ((ch2 == 'N') || (ch2 == 'n')) {
                                clearConsole();


                                op1 = manageItem();
                            }
                        }
                    }
                }


                if (op1 == 2) {
                    clearConsole();
                    topicAdd("DELETE ITEM CATEGORY");

                    do {

                        System.out.print("Enter item category :");
                        String itemName = input.next();

                        int index = searchArray2(category, itemName);

                        if (index == -1) {
                            System.out.println("Cant find the category.please try again with another category");
                            System.out.println();
                        } else {
                            category = deleteArray2(category, index);
                            System.out.print("Sucessfully deleted.Do you want to delete another category?(Y/N)");
                            char ch = input.next().charAt(0);
                            System.out.println();


                            if ((ch == 'Y') || (ch == 'y')) {
                                continue;
                            }


                            if ((ch == 'N') || (ch == 'n')) {
                                clearConsole();
                                op1 = manageItem();
                                break;

                            }

                        }
                    } while (true);
                }


                if (op1 == 3) {
                    clearConsole();
                    topicAdd("UPDATE ITEM CATEGORY");

                    while (true) {
                        System.out.print("Enter item category :");
                        String itemName = input.next();

                        int index = searchArray2(category, itemName);

                        if (index == -1) {
                            System.out.println("Cant find item category .try again!");
                        } else {
                            System.out.println("Category name :" + category[index]);
                            System.out.println();
                            System.out.print("Enter the category name :");
                            String name = input.next();
                            name = category[index];

                            System.out.print("Updated sucessfully! Do you want to update another category(Y/N) :");
                            char ch2 = input.next().charAt(0);
                            System.out.println();


                            if ((ch2 == 'N') || (ch2 == 'n')) {
                                clearConsole();
                                op1 = manageItem();
                                break;
                            }

                        }
                    }
                }


                if (op1 == 4) {
                    clearConsole();
                    option3();
                }
            } //end of manageItems;

//========================end of manageItems==============//

            if (op == 2) {
                clearConsole();
                topicAdd("ADD ITEM");

                int length = arrayLength2(suppliers);

                if (length == 0) {
                    System.out.print("Oops sems like you dont have any suppliers in the system\nDo you want to add a new supplier?(Y/N)");
                    char ch = input.next().charAt(0);
                    System.out.println();

                    if ((ch == 'Y') || (ch == 'y')) {

                        while (true) {
                            System.out.print("Enter supplier Id :");
                            String id = input.next();

                            int index = searchArray(suppliers, id);

                            if (index == -1) {
                                suppliers = incrementArray(suppliers);
                                suppliers[0][suppliers[0].length - 1] = id;


                                System.out.print("Enter the supplier  name:");
                                String name = input.next();

                                suppliers[1][suppliers[1].length - 1] = name;
                            } else {
                                System.out.println("Already exists.pls try another");
                                System.out.println();
                                continue;
                            }


                            System.out.print("Successfully added! Do you want to add another supplier (Y/N)");
                            char ch1 = input.next().charAt(0);
                            System.out.println();


                            if ((ch1 == 'Y') || (ch1 == 'y')) {
                                continue;
                            }//end of ch1=y

                            if ((ch1 == 'N') || (ch1 == 'n')) {
                                //******************//break kak danna unoth supplir whil

                                int length2 = arrayLength(category);

                                if (length2 == 0) {
                                    System.out.print("Oops seems like you dont have any item category in the system\nDo you want to add an item Category?(Y/N):");
                                    char ch2 = input.next().charAt(0);
                                    System.out.println();

                                    while (true) {
                                        System.out.print("Enter the new Item Category :");
                                        String itemName = input.next();

                                        int index2 = searchArray2(category, itemName);
                                        if (index2 != -1) {
                                            System.out.println("This category already exsists. pls try another one!");
                                        } else {
                                            category = incrementArray(category);
                                            category[category.length - 1] = itemName;
                                            System.out.println(Arrays.toString(category));

                                            System.out.print("added sucessfully! Do you want to add another item category? (Y/N)");
                                            char ch3 = input.next().charAt(0);
                                            System.out.println();


                                            if ((ch3 == 'Y') || (ch3 == 'y')) {

                                                continue;
                                            }
                                            if ((ch3 == 'N') || (ch3 == 'n')) {
                                                clearConsole();

                                                break;
                                            }
                                        }

                                    } //end of while


                                }//end of ch1=n

                            }//end of while


                            if ((ch1 == 'N') || (ch1 == 'n')) {
                                continue;
                            }
                        }//end of if(ch==y)

                        /*if ((ch == 'N') || (ch == 'n')) {
                            //System.out.println("A");


                        }*///end of if(ch==n)


                    }//end of length if
                }//option 2


            }//end of while 1
        }//end of option 3
    }

        public static int arrayLength (String[]br){

            return category.length;
        }

        public static int arrayLength2 (String[][]br){

            return br[0].length;

        }


        public static int manageItem () {
            topicAdd("MANAGE ITEM CATEGORIES");


            System.out.println("[1] Add New Item Category \t\t\t\t[2] Delete Item Category");
            System.out.println();
            System.out.println("[3] Update Item Categories \t\t\t\t[4] Stock Management");


            int op1 = getOption();
            return op1;
        }


        public static void main (String[]args){


            login();
            while (true) {

                clearConsole();
                mainOption();
                int op = getOption();
                clearConsole();
                if (op == 1) {
                    option1();
                }
                if (op == 2) {
                    option2();
                }
                if (op == 3) {
                    option3();
                }
            }
        }
    }
