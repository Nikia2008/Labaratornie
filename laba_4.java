import java.util.Scanner;

class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String message) {
        super(message);
    }
}

    
void checkPassword(String password)
        throws InvalidPasswordException {

    if (password.length() < 8) {
        throw new InvalidPasswordException(
                "Ошибка: пароль должен содержать минимум 8 символов!"
        );
    }

    System.out.println("Пароль принят!");
}

void main(){
    Scanner scan = new Scanner(System.in);

    System.out.print("Введите пароль: ");
    String password = scan.nextLine();

    try {
        checkPassword(password);
    } catch (InvalidPasswordException e) {
        System.out.println(e.getMessage());
    }
}