package semester1.practice10.homework;

interface CashbackCapable {
    double MAX_CASHBACK_UAH = 500.0;

    double getCashbackRate();

    default double calculateCashback(double baseAmount) {
        return Math.min(MAX_CASHBACK_UAH, baseAmount * getCashbackRate());
    }
}

interface Refundable {
    boolean requestRefund(String reason);

    boolean isRefunded();
}

interface BlockchainVerifiable {
    String getTxHash();

    int getConfirmations();

    default boolean isFinalized() {
        return getConfirmations() >= 12;
    }
}

abstract class AbstractPaymentTransaction {
    private final String txId;
    private final double baseAmountUah;
    private boolean processed;

    public AbstractPaymentTransaction(String txId, double baseAmountUah) {
        this.txId = txId;
        this.baseAmountUah = baseAmountUah;
        this.processed = false;
    }

    public String getTxId() {
        return txId;
    }

    public double getBaseAmountUah() {
        return baseAmountUah;
    }

    public boolean isProcessed() {
        return processed;
    }

    public abstract double calculateFee();

    public abstract String getPaymentChannel();

    // шаблонний метод - нащадки не можуть його змінити
    public final boolean process() {
        if (baseAmountUah <= 0 || processed) {
            return false;
        }
        processed = true;
        return true;
    }

    public final double getTotalCharged() {
        return baseAmountUah + calculateFee();
    }
}

class PremiumCardPayment extends AbstractPaymentTransaction implements CashbackCapable, Refundable {
    private final String maskedPan;
    private final double cashbackRate;
    private boolean refunded;

    public PremiumCardPayment(String txId, double amount, String maskedPan) {
        super(txId, amount);
        this.maskedPan = maskedPan;
        this.cashbackRate = 0.03;
        this.refunded = false;
    }

    @Override
    public double calculateFee() {
        return getBaseAmountUah() * 0.01;
    }

    @Override
    public String getPaymentChannel() {
        return "Visa Infinite";
    }

    @Override
    public double getCashbackRate() {
        return cashbackRate;
    }

    @Override
    public boolean requestRefund(String reason) {
        if (isProcessed() && !refunded) {
            refunded = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean isRefunded() {
        return refunded;
    }
}

class CryptoUsdtPayment extends AbstractPaymentTransaction implements CashbackCapable, BlockchainVerifiable {
    private final String txHash;
    private final int confirmations;

    public CryptoUsdtPayment(String txId, double amount, String txHash, int confirmations) {
        super(txId, amount);
        this.txHash = txHash;
        this.confirmations = Math.max(0, confirmations);
    }

    @Override
    public double calculateFee() {
        return 40.0;
    }

    @Override
    public String getPaymentChannel() {
        return "USDT TRC-20";
    }

    @Override
    public double getCashbackRate() {
        return 0.01;
    }

    @Override
    public String getTxHash() {
        return txHash;
    }

    @Override
    public int getConfirmations() {
        return confirmations;
    }
}

final class TaxTreasuryPayment extends AbstractPaymentTransaction implements Refundable {
    private final String taxCode;
    private boolean refunded;

    public TaxTreasuryPayment(String txId, double amount, String taxCode) {
        super(txId, amount);
        this.taxCode = taxCode;
        this.refunded = false;
    }

    @Override
    public double calculateFee() {
        return 0.0;
    }

    @Override
    public String getPaymentChannel() {
        return "ДержКазначейство";
    }

    @Override
    public boolean requestRefund(String reason) {
        if (isProcessed() && !refunded) {
            refunded = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean isRefunded() {
        return refunded;
    }
}

public class Task2SmartPaymentAndAuditGateway {

    public static void runAudit(AbstractPaymentTransaction[] ledger) {
        double turnover = 0;
        double fees = 0;
        double cashbackTotal = 0;

        System.out.println("[1. ПРОВЕДЕННЯ ПАКЕТУ ТРАНЗАКЦІЙ (final process())]:");
        for (AbstractPaymentTransaction tx : ledger) {
            if (tx.process()) {
                turnover += tx.getBaseAmountUah();
                fees += tx.calculateFee();
                System.out.printf("  ✅ [%s | %s] Сума: %.2f грн | Комісія: %.2f грн | Всього: %.2f грн%n",
                        tx.getTxId(), tx.getPaymentChannel(), tx.getBaseAmountUah(),
                        tx.calculateFee(), tx.getTotalCharged());
            } else {
                System.out.println("  ❌ [" + tx.getTxId() + "] Транзакцію відхилено");
            }
        }

        System.out.println();
        System.out.println("[2. АВТОМАТИЧНИЙ АУДИТ МОЖЛИВОСТЕЙ (Pattern Matching instanceof)]:");
        for (AbstractPaymentTransaction tx : ledger) {
            if (tx instanceof CashbackCapable c) {
                double raw = tx.getBaseAmountUah() * c.getCashbackRate();
                double cashback = c.calculateCashback(tx.getBaseAmountUah());
                cashbackTotal += cashback;
                if (raw > cashback) {
                    System.out.printf("  💰 [CashbackCapable] %s: розрахунковий %.0f%% = %.0f грн -> нараховано %.2f грн (Ліміт MAX_CASHBACK_UAH!)%n",
                            tx.getTxId(), c.getCashbackRate() * 100, raw, cashback);
                } else {
                    System.out.printf("  💰 [CashbackCapable] %s: нараховано %.0f%% кешбеку = %.2f грн%n",
                            tx.getTxId(), c.getCashbackRate() * 100, cashback);
                }
            }
            if (tx instanceof BlockchainVerifiable b) {
                System.out.println("  ⛓️ [BlockchainVerifiable] " + tx.getTxId() + ": Hash=" + b.getTxHash()
                        + ", Підтверджень=" + b.getConfirmations() + " (Finalized: " + b.isFinalized() + ")");
            }
        }

        // тестове повернення для першої транзакції, яка його підтримує
        for (AbstractPaymentTransaction tx : ledger) {
            if (tx instanceof Refundable r) {
                boolean ok = r.requestRefund("Подвійне списання");
                System.out.println("  🔄 [Refundable] Спроба повернення " + tx.getTxId() + " ('Подвійне списання'): "
                        + (ok ? "УСПІШНО ПОВЕРНЕНО!" : "ВІДМОВА"));
                boolean again = r.requestRefund("Повторний запит");
                System.out.println("  🔄 [Refundable] Повторна спроба " + tx.getTxId() + ": "
                        + (again ? "ПОВЕРНЕНО" : "ВІДМОВА (вже повернено)"));
                break;
            }
        }

        System.out.println();
        System.out.println("[ПІДСУМКОВИЙ ЗВІТ ШЛЮЗУ]:");
        System.out.printf("  Сумарний базовий оборот:  %.2f грн%n", turnover);
        System.out.printf("  Сумарна комісія шлюзу:    %.2f грн%n", fees);
        System.out.printf("  Сумарний виплачений кешбек: %.2f грн%n", cashbackTotal);
    }

    public static void main(String[] args) {
        System.out.println("================ ПРОЦЕСИНГОВИЙ ЦЕНТР FINTECH (POLYMORPHISM & INTERFACES) ================");

        AbstractPaymentTransaction[] ledger = {
                new PremiumCardPayment("TX-101", 20000, "4149 **** **** 7712"),
                new CryptoUsdtPayment("TX-102", 8500, "0x9f8a...c4", 18),
                new TaxTreasuryPayment("TX-103", 5000, "ПДФО-1101")
        };

        runAudit(ledger);
        System.out.println("=========================================================================================");
    }
}
