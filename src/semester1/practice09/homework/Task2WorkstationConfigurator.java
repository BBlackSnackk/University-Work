package semester1.practice09.homework;

import java.util.Arrays;

class Motherboard {
    private final String model;
    private final String socketType;
    private final int maxExpansionSlots;

    public Motherboard(String model, String socketType, int maxExpansionSlots) {
        this.model = model;
        this.socketType = socketType;
        this.maxExpansionSlots = Math.max(1, Math.min(8, maxExpansionSlots));
    }

    public String getModel() {
        return model;
    }

    public String getSocketType() {
        return socketType;
    }

    public int getMaxExpansionSlots() {
        return maxExpansionSlots;
    }

    public boolean isSocketCompatible(String cpuSocket) {
        return cpuSocket != null && socketType.equalsIgnoreCase(cpuSocket);
    }
}

class PowerSupplyUnit {
    private final String model;
    private final int maxOutputWatts;
    private final double efficiencyCoefficient;

    public PowerSupplyUnit(String model, int maxOutputWatts, double efficiencyCoefficient) {
        this.model = model;
        this.maxOutputWatts = Math.max(100, maxOutputWatts);
        this.efficiencyCoefficient = Math.max(0.80, Math.min(0.96, efficiencyCoefficient));
    }

    public String getModel() {
        return model;
    }

    public double getEfficiencyCoefficient() {
        return efficiencyCoefficient;
    }

    public int getSafeContinuousWatts() {
        return (int) (maxOutputWatts * 0.85);
    }

    public double calculateWallPowerDraw(int internalLoadWatts) {
        return internalLoadWatts / efficiencyCoefficient;
    }
}

abstract class AbstractHardwareComponent {
    private final String modelName;
    private final double priceUah;

    public AbstractHardwareComponent(String modelName, double priceUah) {
        this.modelName = modelName;
        this.priceUah = (priceUah >= 0) ? priceUah : 0;
    }

    public String getModelName() {
        return modelName;
    }

    public double getPriceUah() {
        return priceUah;
    }

    public abstract int getPowerWatts();

    public abstract double getBenchmarkScore();

    public abstract String getCategory();

    @Override
    public String toString() {
        return "[" + getCategory() + "] " + modelName + " (" + getPowerWatts() + " Вт | Score: " + getBenchmarkScore() + ")";
    }
}

class ProcessorUnit extends AbstractHardwareComponent {
    private final String socket;
    private final int cores;
    private final double boostClockGhz;
    private final int tdpWatts;

    public ProcessorUnit(String modelName, double priceUah, String socket, int cores, double boostClockGhz, int tdpWatts) {
        super(modelName, priceUah);
        this.socket = socket;
        this.cores = Math.max(1, cores);
        this.boostClockGhz = (boostClockGhz > 0) ? boostClockGhz : 1.0;
        this.tdpWatts = Math.max(1, tdpWatts);
    }

    public String getSocket() {
        return socket;
    }

    @Override
    public int getPowerWatts() {
        return tdpWatts;
    }

    @Override
    public double getBenchmarkScore() {
        return cores * boostClockGhz * 120.0;
    }

    @Override
    public String getCategory() {
        return "CPU";
    }
}

class GraphicsAccelerator extends AbstractHardwareComponent {
    private final int vramGb;
    private final int tensorCores;
    private final int boardPowerWatts;

    public GraphicsAccelerator(String modelName, double priceUah, int vramGb, int tensorCores, int boardPowerWatts) {
        super(modelName, priceUah);
        this.vramGb = Math.max(1, vramGb);
        this.tensorCores = Math.max(0, tensorCores);
        this.boardPowerWatts = Math.max(1, boardPowerWatts);
    }

    @Override
    public int getPowerWatts() {
        return boardPowerWatts;
    }

    @Override
    public double getBenchmarkScore() {
        return vramGb * 250.0 + tensorCores * 1.5;
    }

    @Override
    public String getCategory() {
        return "GPU";
    }
}

class NvmeSsdDrive extends AbstractHardwareComponent {
    private final int capacityGb;
    private final int readSpeedMbSec;
    private final int[] speedSamples;

    public NvmeSsdDrive(String modelName, double priceUah, int capacityGb, int readSpeedMbSec, int[] speedSamples) {
        super(modelName, priceUah);
        this.capacityGb = Math.max(1, capacityGb);
        this.readSpeedMbSec = Math.max(1, readSpeedMbSec);
        // захисна копія на вході
        if (speedSamples == null) {
            this.speedSamples = new int[0];
        } else {
            this.speedSamples = Arrays.copyOf(speedSamples, speedSamples.length);
        }
    }

    public int[] getSpeedSamples() {
        return Arrays.copyOf(speedSamples, speedSamples.length); // захисна копія на виході
    }

    @Override
    public int getPowerWatts() {
        return 12;
    }

    @Override
    public double getBenchmarkScore() {
        return readSpeedMbSec * 0.25;
    }

    @Override
    public String getCategory() {
        return "NVMe SSD";
    }
}

class WorkstationPC {
    private final String buildName;
    private final Motherboard motherboard;
    private final PowerSupplyUnit psu;
    private final AbstractHardwareComponent[] components;
    private int componentCount;

    public WorkstationPC(String buildName, Motherboard motherboard, PowerSupplyUnit psu) {
        if (motherboard == null || psu == null) {
            throw new IllegalArgumentException("Плата і блок живлення обов'язкові!");
        }
        this.buildName = buildName;
        this.motherboard = motherboard;
        this.psu = psu;
        this.components = new AbstractHardwareComponent[motherboard.getMaxExpansionSlots()];
        this.componentCount = 0;
    }

    public boolean installComponent(AbstractHardwareComponent comp) {
        if (comp == null) {
            System.out.println("❌ Відмова: компонент не може бути null!");
            return false;
        }
        if (componentCount >= motherboard.getMaxExpansionSlots()) {
            System.out.println("❌ Відмова: немає вільних слотів для " + comp.getModelName() + "!");
            return false;
        }
        if (comp.getCategory().equals("CPU")) {
            ProcessorUnit cpu = (ProcessorUnit) comp;
            if (!motherboard.isSocketCompatible(cpu.getSocket())) {
                System.out.println("❌ Відмова: Процесор " + cpu.getModelName() + " (" + cpu.getSocket()
                        + ") несумісний із платою " + motherboard.getModel()
                        + " (Сокет " + motherboard.getSocketType() + ")!");
                return false;
            }
        }
        int newPower = getTotalPowerWatts() + comp.getPowerWatts();
        if (newPower > psu.getSafeContinuousWatts()) {
            System.out.println("❌ Відмова: " + comp.getModelName() + " перевантажить БЖ ("
                    + newPower + " Вт > " + psu.getSafeContinuousWatts() + " Вт)!");
            return false;
        }
        components[componentCount] = comp;
        componentCount++;
        System.out.println("✅ Встановлено: " + comp);
        return true;
    }

    public int getTotalPowerWatts() {
        int sum = 0;
        for (int i = 0; i < componentCount; i++) {
            sum += components[i].getPowerWatts();
        }
        return sum;
    }

    public double getTotalSystemScore() {
        double sum = 0;
        for (int i = 0; i < componentCount; i++) {
            sum += components[i].getBenchmarkScore();
        }
        return sum;
    }

    public double getTotalBuildPrice() {
        double sum = 0;
        for (int i = 0; i < componentCount; i++) {
            sum += components[i].getPriceUah();
        }
        return sum;
    }

    @Override
    public String toString() {
        int power = getTotalPowerWatts();
        String status = (power <= psu.getSafeContinuousWatts()) ? "СТАБІЛЬНО!" : "ПЕРЕВАНТАЖЕННЯ!";
        String result = "[ПАСПОРТ ЗБІРКИ '" + buildName + "']:\n";
        result += "  Материнська плата: " + motherboard.getModel() + " (Socket: " + motherboard.getSocketType()
                + ", Слотів: " + componentCount + "/" + motherboard.getMaxExpansionSlots() + ")\n";
        result += String.format("  Блок живлення:     %s (Безпечний ліміт 85%%: %d Вт, ККД: %.0f%%)%n",
                psu.getModel(), psu.getSafeContinuousWatts(), psu.getEfficiencyCoefficient() * 100);
        result += String.format("  Внутрішнє споживання: %d Вт (З розетки з урахуванням ККД: %.2f Вт) -> %s%n",
                power, psu.calculateWallPowerDraw(power), status);
        result += String.format("  Сумарний індекс продуктивності: %.2f балів%n", getTotalSystemScore());
        result += String.format("  Загальна вартість конфігурації: %.2f грн%n", getTotalBuildPrice());
        return result;
    }
}

public class Task2WorkstationConfigurator {
    public static void main(String[] args) {
        System.out.println("================ КОНФІГУРАТОР РОБОЧОЇ СТАНЦІЇ (КОМПОЗИЦІЯ) ================");

        Motherboard board = new Motherboard("ASUS ROG B650", "AM5", 4);
        PowerSupplyUnit psu = new PowerSupplyUnit("Seasonics 750W Gold", 750, 0.90);
        WorkstationPC pc = new WorkstationPC("DeepLearning-Rig", board, psu);

        ProcessorUnit intel = new ProcessorUnit("Intel Core i9", 22000, "LGA1700", 24, 6.0, 125);
        ProcessorUnit ryzen = new ProcessorUnit("AMD Ryzen 9 7950X", 24900, "AM5", 16, 5.7, 170);
        GraphicsAccelerator rtx4080 = new GraphicsAccelerator("NVIDIA RTX 4080 Super", 46000, 16, 552, 320);
        GraphicsAccelerator rtx4090 = new GraphicsAccelerator("NVIDIA RTX 4090", 95000, 24, 512, 450);

        int[] samples = {7400, 7450, 7380};
        NvmeSsdDrive ssd = new NvmeSsdDrive("Samsung 990 Pro 2TB", 8000, 2000, 7450, samples);

        System.out.println("[ПЕРЕВІРКА СУМІСНОСТІ]:");
        pc.installComponent(intel);
        pc.installComponent(ryzen);
        pc.installComponent(rtx4080);
        pc.installComponent(ssd);
        pc.installComponent(rtx4090); // не влізе по потужності БЖ

        System.out.println();
        System.out.print(pc);

        System.out.println();
        System.out.println("[ПЕРЕВІРКА ЗАХИСНОГО КОПІЮВАННЯ SSD]:");
        samples[0] = 1;
        int[] leaked = ssd.getSpeedSamples();
        leaked[1] = -999;
        System.out.println("  Зовнішній масив змінили: " + Arrays.toString(samples));
        System.out.println("  Копію з геттера змінили: " + Arrays.toString(leaked));
        System.out.println("  Всередині SSD залишилось: " + Arrays.toString(ssd.getSpeedSamples()));
        System.out.println("===========================================================================");
    }
}
