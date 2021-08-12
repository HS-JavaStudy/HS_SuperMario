package SuperMario;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Music extends Thread{

	private Player player; // �ܺ� ���̺귯��(javazoom.jil)�� ���ǵǾ� ����
	private boolean isLoop; // ���� ���ѹݺ��� ���� ����(����)
	private File file;
	private FileInputStream fls;
	private BufferedInputStream bls;
	
	public Music(String name, boolean isLoop) { // �� ����� ���ѹݺ� ���� �ޱ�
		
		try {
			// ���� �ʱ�ȭ
			this.isLoop = isLoop; 
			file = new File("src/music/"+ name); // file ��ν��ְ� ��ü�� ����
			fls = new FileInputStream(file);
			bls = new BufferedInputStream(fls); // �ش� ������ ���ۿ� ��Ƽ� �о��
			player = new Player(bls); // ���ۿ� ��ƿ� ������ ��´�
			
		}catch(Exception e) { // ����ó��
			
			System.out.println(e.getMessage());
			
		}
	}
	
	public void close() { // ���� �����ϰ� ����
		
		 isLoop = false;
		 player.close();
		 this.interrupt(); // �ش� �����带 �������·� ���� (�� ���� ����)
		
	}
	
	@Override
	public void run() { // �����带 ��ӹ�����  ������ ����ؾ� �ϴ� �Լ�
		
		try {
			
			do {
				player.play(); // ���� ����
				fls = new FileInputStream(file);
				bls = new BufferedInputStream(fls); // �ش� ������ ���ۿ� ��Ƽ� �о��
				player = new Player(bls); // ���ۿ� ��ƿ� ������ ��´�
				
			}while(isLoop); // isLoop�� true�̸� ���� �ݺ�
			
		}catch(Exception e) {
			
			System.out.println(e.getMessage()); // ���� �߻��� �ش� �������� ���
			
		}
		
		
		
	}
}