package SuperMario;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Music extends Thread{

	private Player player; // 외부 라이브러리(javazoom.jil)에 정의되어 있음
	private boolean isLoop; // 음악 무한반복에 대한 설정(변수)
	private File file;
	private FileInputStream fls;
	private BufferedInputStream bls;
	
	public Music(String name, boolean isLoop) { // 곡 제목과 무한반복 여부 받기
		
		try {
			// 변수 초기화
			this.isLoop = isLoop; 
			file = new File("src/music/"+ name); // file 경로써주고 객체에 담음
			fls = new FileInputStream(file);
			bls = new BufferedInputStream(fls); // 해당 파일을 버퍼에 담아서 읽어옴
			player = new Player(bls); // 버퍼에 담아온 파일을 담는다
			
		}catch(Exception e) { // 예외처리
			
			System.out.println(e.getMessage());
			
		}
	}
	
	public void close() { // 음악 종료하게 만듦
		
		 isLoop = false;
		 player.close();
		 this.interrupt(); // 해당 스레드를 중지상태로 만듦 (곡 실행 종료)
		
	}
	
	@Override
	public void run() { // 스레드를 상속받으면  무조건 사용해야 하는 함수
		
		try {
			
			do {
				player.play(); // 음악 실행
				fls = new FileInputStream(file);
				bls = new BufferedInputStream(fls); // 해당 파일을 버퍼에 담아서 읽어옴
				player = new Player(bls); // 버퍼에 담아온 파일을 담는다
				
			}while(isLoop); // isLoop가 true이면 무한 반복
			
		}catch(Exception e) {
			
			System.out.println(e.getMessage()); // 오류 발생시 해당 오류내용 출력
			
		}
		
		
		
	}
}
