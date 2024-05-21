package progSD;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Controller
public class FileUploadController {

	private final StorageService storageService;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	@GetMapping("/immagineProva/")
	public String listUploadedFiles(Model model) throws IOException {
		model.addAttribute("files", storageService.loadAll().map(
				path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
						"serveFile", path.getFileName().toString()).build().toUri().toString())
				.collect(Collectors.toList()));
		return "uploadForm";
	}

	@GetMapping("/immagine/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	
	@PostMapping("/immagine/{idTile}/")
	public String handleFileUpload(@PathVariable String idTile, @RequestParam("file") MultipartFile file) throws IOException {
		System.out.println(file.toString() + "CIAO");
//		if(file == "") {
//			System.out.println(file);
//			return "redirect:/";
//		}
        File immagineVecchia = new File("./storage/" + idTile + ".jpg");
        if (immagineVecchia.exists()) {
        	immagineVecchia.delete();
        }
        
        storageService.store(file);
        File immagine = new File("./tmp/" + file.getOriginalFilename());
        
        BufferedImage originalImage = ImageIO.read(immagine);
        int typeImage = originalImage .getType();
        
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        
        int width = 900;
        int height = 900;
        
        BufferedImage resizedImage;
        
        if(originalWidth > 900 && originalHeight > 900) {
        	resizedImage = new BufferedImage(width, height, typeImage);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, width, height, null);
            g.dispose();
        }
        else if(originalWidth <= 900 && originalHeight > 900) {
        	resizedImage = new BufferedImage(originalWidth, height, typeImage);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, originalWidth, height, null);
            g.dispose();
        }
        else if(originalWidth > 900 && originalHeight <= 900) {
        	resizedImage = new BufferedImage(width, originalHeight, typeImage);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, width, originalHeight, null);
            g.dispose();
        }
        else {
        	resizedImage = new BufferedImage(originalWidth, originalHeight, typeImage);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, originalWidth, originalHeight, null);
            g.dispose();
        }
         
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage , "jpg", baos);
        baos.flush();
        
        //imageInByte è il nostro byte[]
        byte[] imageInByte = baos.toByteArray();
        
        FileOutputStream outputStream = new FileOutputStream(immagine);
        outputStream.write(imageInByte);
        
        outputStream.close();
        baos.close();
        
        immagine.renameTo(new File("./storage/" + idTile + ".jpg"));
        return "redirect:/";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
}