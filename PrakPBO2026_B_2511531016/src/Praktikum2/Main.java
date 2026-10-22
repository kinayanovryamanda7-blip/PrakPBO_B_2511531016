package Praktikum2;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Rekening> daftarRekening = new ArrayList<>();
        Rekening akunAktif = null; // Objek belum diinisialisasi (null)
        boolean isRunning = true;

        System.out.println("=== SISTEM PERBANKAN MINI ===");

        while (isRunning) {
            System.out.println("\n=== Menu Utama ===");
            System.out.println("1. Buka Rekening Baru");
            System.out.println("2. Setor Tunai");
            System.out.println("3. Tarik Tunai");
            System.out.println("4. Cek Informasi Rekening");
            System.out.println("5. Ganti Akun");
            System.out.println("6. Cetak Mutasi (Riwayat)");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");

            int pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {

            case 1:
                System.out.print("Masukkan No Rekening: ");
                String no = input.nextLine();
                System.out.print("Masukkan Nama Pemilik: ");
                String nama = input.nextLine();
                System.out.print("Masukkan Saldo Awal: ");
                double saldo = input.nextDouble();

                // Instansiasi Object / Menjalankan Constructor
                Rekening rekeningBaru = new Rekening(no, nama, saldo);
                daftarRekening.add(rekeningBaru);
                akunAktif = rekeningBaru;
                System.out.println("Rekening berhasil ditambahkan.");
                break;

            case 2:
                if (akunAktif == null) {
                    System.out.println("Error: Mohon maaf, Anda belum memiliki nomor rekening!");
                } else {
                    System.out.print("Masukkan nominal setor: ");
                    double setor = input.nextDouble();
                    akunAktif.setorTunai(setor);
                    }
                break;

            case 3:
                if (akunAktif == null) {
                    System.out.println("Error: Anda belum membuka rekening!");
                } else {
                    System.out.print("Masukkan nominal tarik: ");
                    double tarik = input.nextDouble();
                    akunAktif.tarikTunai(tarik);
                    }
                break;

            case 4:
                if (akunAktif == null) {
                    System.out.println("Error: Anda belum membuka rekening!");
                } else {
                    akunAktif.cekInformasi();
                }
                break;
                
            case 5 :
            	if (daftarRekening.isEmpty()) {
    				System.out.println("Belum ada rekening yang tersedia.");
    			} else {
    				System.out.print("Masukkan nomor rekening yang ingin digunakan: ");
    				String nomorCari = input.nextLine();
    				boolean ditemukan = false;
    				for (Rekening rekening : daftarRekening) {
    					if (rekening.nomorRekening.equals(nomorCari)) {
    						akunAktif = rekening;
    						ditemukan = true;
    						System.out.println("Berhasil mengganti akun.");
    						System.out.println("Akun aktif: "+ akunAktif.namaPemilik);
    						break;
    					}
    				}
    				if (!ditemukan) {
    					System.out.println("Rekening tidak ditemukan.");
    				}
    			}
            	break;	
            
            case 6:
                if (akunAktif == null) {
                    System.out.println("Error: Anda belum membuka rekening!");
                } else {
                    akunAktif.cetakMutasi();
                }
                break;

            case 0:
                isRunning = false;
                System.out.println("Sistem ditutup. Terima kasih!");
                break;

            default:
                System.out.println("Pilihan tidak valid!");
            }
        }

        input.close();
    }
}