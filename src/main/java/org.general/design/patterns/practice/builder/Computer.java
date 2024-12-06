package design.patterns.practice.builder;

import java.util.Objects;

public class Computer {

    //Mandatory parts
    private String cpu;
    private String ram;
    private String motherboard;

    //Additional components
    private String graphicsCard;
    private String bluetooth;

    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.motherboard = builder.motherboard;
        if (!Objects.isNull(builder.graphicsCard)) {
            this.graphicsCard = builder.graphicsCard;
        } else {
            this.graphicsCard = "No Graphics";
        }
        if (!Objects.isNull(builder.bluetooth)) {
            this.bluetooth = builder.bluetooth;
        } else {
            this.bluetooth = "No Bluetooth";
        }
    }

    public String getCpu() {
        return cpu;
    }

    public String getRam() {
        return ram;
    }

    public String getMotherboard() {
        return motherboard;
    }

    public String getGraphicsCard() {
        return graphicsCard;
    }

    public String getBluetooth() {
        return bluetooth;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "cpu='" + cpu + '\'' +
                ", ram='" + ram + '\'' +
                ", motherboard='" + motherboard + '\'' +
                ", graphicsCard='" + graphicsCard + '\'' +
                ", bluetooth='" + bluetooth + '\'' +
                '}';
    }

    public static class Builder {
        //Mandatory parts
        private String cpu;
        private String ram;
        private String motherboard;

        //Additional components
        private String graphicsCard;
        private String bluetooth;

        public Builder(String cpu, String ram, String motherboard) {
            this.cpu = cpu;
            this.ram = ram;
            this.motherboard = motherboard;
        }

        public Builder setGraphicsCard(String graphicsCard) {
            this.graphicsCard = graphicsCard;
            return this;
        }

        public Builder setBluetooth(String bluetooth) {
            this.bluetooth = bluetooth;
            return this;
        }

        public Computer create() {
            return new Computer(this);
        }
    }

    public static void main(String[] args) {
        Computer computer = new Computer.Builder("Intel", "16GB", "Gigabyte")
                .setBluetooth("5.1")
                .setGraphicsCard("Nvidia")
                .create();
        System.out.println(computer);
    }

}
