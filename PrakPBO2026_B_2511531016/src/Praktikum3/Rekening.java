package Praktikum3;
import java.util.Scanner;
import java.util.ArrayList;

public class Rekening {
	// 1. Mengunci atribut dengan 'private'
    private String nomorRekening;
    private String namaPemilik;
    private double saldo;
    private String pin; // Data sensitif
    
    private ArrayList<Transaksi> riwayatTransaksi;

    // 2. Modifikasi Constructor untuk menerima PIN awal
    public Rekening(String nomor, String nama, double saldoAwal, String pinAwal) {
        this.nomorRekening = nomor;
        this.namaPemilik = nama;
        this.saldo = saldoAwal;
        
        // Validasi PIN di dalam Constructor
        if (pinAwal.length() == 6) {
        	this.pin = pinAwal;
        } else {
        	System.out.println("Peringatan: PIN harus 6 digit! Menggunakan PIN default 123456");
        	this.pin = "123456";
        }
        
        this.riwayatTransaksi = new ArrayList<>();
        riwayatTransaksi = new ArrayList<>();
        System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat.");
    }

    // 3. Getter untuk atribut yang diizinkan dibaca publik
    public String getNomorRekening() { return nomorRekening; }
    public String getNamaPemilik() { return namaPemilik; }
    
    // 4. Method Otontikasi Internal (Validasi Enkapsulasi)
    public boolean otentikasi(String inputPin) {
    	return this.pin.equals(inputPin);
    }
    
    public void setorTunai(double nominal) {
        if (nominal > 0) {
            saldo += nominal;
            String idTrx = "TRX-S-" + System.currentTimeMillis();
            Transaksi trxBaru = new Transaksi(idTrx, "Kredit", nominal);
            riwayatTransaksi.add(trxBaru);
            System.out.println("Setor tunai Rp" + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
        } else {
            System.out.println("Gagal: Nominal setor harus lebih dari 0!");
        }
    }

    public void tarikTunai(double nominal) {
    	if (nominal < 10000) { 
    		System.out.println("Transaksi Gagal : Minimal nomonal penarikan 10.000"); 
    	} else if (nominal > saldo) { 
    		System.out.println("Transaksi Gagal: Saldo tidak mencukupi. Saldo Anda: Rp" + saldo); 
    	} else { saldo -= nominal; 
    		System.out.println("Tarik tunai berhasil."); 
    		System.out.println("Jumlah penarikan: Rp" + nominal); 
    		System.out.println("Saldo sekarang: Rp" + saldo); 
    		
            String idTrx = "TRX-T-" + System.currentTimeMillis();
            Transaksi trxBaru = new Transaksi(idTrx, "Debit", nominal);
            riwayatTransaksi.add(trxBaru);
        }
    }

    public void cekInformasi() {
        System.out.println("=== INFO REKENING ===");
        System.out.println("No. Rekening : " + nomorRekening);
        System.out.println("Nama Pemilik : " + namaPemilik);
        System.out.println("Saldo Akhir  : Rp" + saldo);
        System.out.println("=====================");
    }

    public void cetakMutasi() {
        if (riwayatTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi pada rekening ini");
        } else {
            for (Transaksi transaksi : riwayatTransaksi) {
                transaksi.cetakDetail();
            }
        }
    }
}