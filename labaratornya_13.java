interface Product {
    String name();
    double price();
    double mark();
}

class NumOne implements Product {
    String name;
    double price;
    double mark;

    NumOne(String name, double price, double mark) {
        this.name = name;
        this.price = price;
        this.mark = mark;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public double price() {
        return this.price;
    }

    @Override
    public double mark() {
        return this.mark;
    }
}

class NumTwo implements Product {
    String name;
    double price;
    double mark;

    NumTwo(String name, double price, double mark) {
        this.name = name;
        this.price = price;
        this.mark = mark;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public double price() {
        return this.price;
    }

    @Override
    public double mark() {
        return this.mark;
    }
}

class NumThree implements Product {
    String name;
    double price;
    double mark;

    NumThree(String name, double price, double mark) {
        this.name = name;
        this.price = price;
        this.mark = mark;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public double price() {
        return this.price;
    }

    @Override
    public double mark() {
        return this.mark;
    }
}


void main() {
    Product numOne = new NumOne("Сыр", 239.99, 3.9);
    Product numTwo = new NumTwo("Колбасу", 135.50, 4.0);
    Product numThree = new NumThree("Молоко", 250.5, 3.35);

    User userOne = new UserOne("Магомед");
    User userTwo = new UserTwo("Паша");
    User userThree = new UserThree("Омар");

    AllClass a1 = new AllClass("0", 0, 0, "0");
    a1.last(userOne, numOne);
    a1.last(userTwo, numTwo);
    a1.last(userThree, numThree);

}


interface User {
    void login();
    void pass();
    String nick();
}

class UserOne implements User {
    String nick;

    UserOne(String nick) {
        this.nick = nick;
    }

    @Override
    public void login() {
        System.out.println(this.nick + " logged");
    }

    @Override
    public void pass() {
        System.out.println(this.nick + " passed");
    }

    @Override
    public String nick() {
        return this.nick;
    }
}

class UserTwo implements User {
    String nick;

    UserTwo(String name) {
        this.nick = name;
    }

    @Override
    public void login() {
        System.out.println(this.nick + " logged");
    }

    @Override
    public void pass() {
        System.out.println(this.nick + " passed");
    }

    @Override
    public String nick() {
        return this.nick;
    }
}

class UserThree implements User {
    String nick;

    UserThree(String name) {
        this.nick = name;
    }

    @Override
    public void login() {
        System.out.println(this.nick + " logged");
    }

    @Override
    public void pass() {
        System.out.println(this.nick + " passed");
    }

    @Override
    public String nick() {
        return this.nick;
    }
}

class AllClass implements Product, User {
    String name;
    double price;
    double mark;
    String nick;

    AllClass(String name, double price, double mark, String nick) {
        this.name = name;
        this.price = price;
        this.mark = mark;
        this.nick = nick;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public double price() {
        return this.price;
    }

    @Override
    public double mark() {
        return this.mark;
    }

    @Override
    public void login() {
        System.out.println(this.nick + " logged");
    }

    @Override
    public void pass() {
        System.out.println(this.nick + " passed");
    }

    @Override
    public String nick() {
        return this.nick;
    }

    public void last(User user, Product product) {
        System.out.println(user.nick() + " купил " + product.name());
    }

}
