import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger counter3 = new AtomicInteger();
    public static AtomicInteger counter4 = new AtomicInteger();
    public static AtomicInteger counter5 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindrom = new Thread(() -> {
            for (String nickName : texts) {
                if (isPalindrom(nickName) && !isOneLetter(nickName)) {
                    count(nickName.length());
                }
            }
        });
        palindrom.start();

        Thread alphabet = new Thread(() -> {
            for (String nickName : texts) {
                if (isAlphabet(nickName) && !isOneLetter(nickName)) {
                    count(nickName.length());
                }
            }
        });
        alphabet.start();

        Thread oneLetter = new Thread(() -> {
            for (String nickName : texts) {
                if (isOneLetter(nickName)) {
                    count(nickName.length());
                }
            }
        });
        oneLetter.start();

        palindrom.join();
        alphabet.join();
        oneLetter.join();

        System.out.println("Красивых слов с длиной 3: " + counter3 + " шт.");
        System.out.println("Красивых слов с длиной 4: " + counter4 + " шт.");
        System.out.println("Красивых слов с длиной 5: " + counter5 + " шт.");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrom(String nickName) {
        return nickName.contentEquals(new StringBuilder(nickName).reverse());
    }

    public static boolean isOneLetter(String nickName) {
        for (int i = 0; i < nickName.length() - 1; i++) {
            if (nickName.charAt(i) != nickName.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlphabet(String nickName) {
        for (int i = 0; i < nickName.length() - 1; i++) {
            if (nickName.charAt(i) > nickName.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static void count(int length) {
        switch (length) {
            case 3:
                counter3.getAndIncrement();
                break;
            case 4:
                counter4.getAndIncrement();
                break;
            case 5:
                counter5.getAndIncrement();
                break;
        }
    }
}