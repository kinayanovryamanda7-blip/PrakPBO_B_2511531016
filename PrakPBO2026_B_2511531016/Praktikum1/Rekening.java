package Praktikum1;
import java.util.Scanner;
import java.util.ArrayList;

public class Rekening {
	String nomorRekening;
	String namaPemilik;
	double saldo;
	
public Rekening(String nomor, String nama, double saldoAwal) {
	nomorRekening = nomor;
	namaPemilik = nama;
	saldo = saldoAwal;
	System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo Rp" + saldo);
}

public void setorTunai(double nominal) {
	if (nominal > 0) {
		saldo += nominal;
		System.out.println("Setor tunai Rp" + nominal + "berhasil. Saldo saat ini: Rp" + saldo);
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
	} 
}

public void cekInformasi() {
	System.out.println("--- INFO REKENING ---");
	System.out.println("No. Rekening : " + nomorRekening);
	System.out.println("Nama Pemilik : " + namaPemilik);
	System.out.println("Saldo Akhir : Rp" + saldo);
	System.out.println("---------------------");
}

public static void main(String[] args) {
	Scanner input = new Scanner(System.in);
	ArrayList<Rekening> daftarRekening = new ArrayList<>();
	Rekening akunAktif = null;
	boolean isRunning = true;
		
	System.out.println("=== SISTEM PERBANKAN MINI ===");
		
	while (isRunning) {
		System.out.println("\nMenu Utama:");
		System.out.println("1. Buka rekening Baru");
		System.out.println("2. Setor Tunai");
		System.out.println("3. Tarik Tunai");
		System.out.println("4. Cek Informasi Rekening");
		System.out.println("5. Ganti Akun");
		System.out.println("0. Keluar");
		System.out.print("Pilih menu: ");
			
		int pilihan = input.nextInt();
		input.nextLine(); // Membersihkan buffer enter
			
		switch (pilihan) {
		case 1:
			System.out.print("Masukkan No Rekening: ");
			String no = input.nextLine();
			System.out.print("Masukkan Nama Pemilik: ");
			String nama = input.nextLine();
			System.out.print("Masukkan Saldo Awal: ");
			double saldo = input.nextDouble();
			
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
				akunAktif.setorTunai(setor); // Memanggil Behavior / method
			}
			break;
				
		case 3:
			 if (akunAktif == null) {
                 System.out.println("Error: Anda belum membuka rekening!");
             } else {
                 System.out.print("Masukkan nominal tarik tunai: Rp");
                 double nominalTarik = input.nextDouble();
                 akunAktif.tarikTunai(nominalTarik);
             }
             break;
				
		case 4:
			if (akunAktif == null) {
				System.out.println("Error: Anda belum membuka rekening!");				
			} else {
				akunAktif.cekInformasi();
			}
			break;
			
		case 5:
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
            
		case 0:
			isRunning = false;
			System.out.println("Sistem ditutup. Terima kasih!");
			break;
				
		default:
			System.out.println("Pilihan tidak valid!");
			}
		}
	}
}