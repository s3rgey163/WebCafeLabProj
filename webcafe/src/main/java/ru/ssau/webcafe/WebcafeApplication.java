package ru.ssau.webcafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ssau.webcafe.entity.Product;
import ru.ssau.webcafe.entity.ProductCategory;
import ru.ssau.webcafe.entity.Volume;
import ru.ssau.webcafe.entity.VolumeType;

@SpringBootApplication
public class WebcafeApplication {

	public static void main(String[] args) {
//		SpringApplication.run(WebcafeApplication.class, args);
		Volume volume = new Volume(200, VolumeType.ML);
		Volume volume2 = new Volume(1, VolumeType.L);
		System.out.println(volume);
	}

}
