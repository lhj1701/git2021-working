package com.git.myworkspace.photo;

//import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.git.myworkspace.lib.TextProcesser;

@RestController
public class PhotoController {

	private PhotoRepository repo;
	private PhotoCommentRepository cmtrepo;

	// Autowired 어노테이션은 매개변수나 필드 타입에 맞는 객체를 스프링에서 생성하여 주입해줌(의존성 주입, 의존객체주입,
	// DI-dependency injection)
	// Repository 인터페이스 구조에 맞는 객체를 스프링에 생성하여 넣어줌
	@Autowired
	public PhotoController(PhotoRepository repo) {
		this.repo = repo;
	}

	@GetMapping(value = "/photos")
	public List<Photo> getPhotos() throws InterruptedException {
		// select * from photo; 와 같음
		// 기본적으로 pk 순정렬(ascs, ascending)
//		return repo.findAll();

		// id 컬럼 역정렬(clusted index)
		// Sort.by("정렬컬럼").descending() 역정렬
		// Sort.by("정렬컬럼").ascending() 순정렬
		return repo.findAll(Sort.by("id").descending());
	}

	// 한 페이지 2개. 1번째 페이지
	// GET /photos/paging?page=0&size=2
	@GetMapping("/photos/paging")
	public Page<Photo> getPhotosPaging(@RequestParam int page, @RequestParam int size) {
		// findAll(Pageable page)
		// findAll(PageRequest.of(page, size, sort))

		return repo.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
	}

	@PostMapping(value = "/photos")
	public Photo addPhoto(@RequestBody Photo photo, HttpServletResponse res) throws InterruptedException {

		// 타이틀이 빈값
		if (TextProcesser.isEmpyText(photo.getTitle())) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		// 파일URL이 빈값
		if (TextProcesser.isEmpyText(photo.getPhotoUrl())) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		// 객체 생성
		Photo photoItem = Photo.builder().title(photo.getTitle())
				.description(TextProcesser.getPlainText(photo.getDescription())).photoUrl(photo.getPhotoUrl())
				.fileType(photo.getFileType()).fileName(photo.getFileType()).createdTime(new Date().getTime()).build();

		// insert into photo(...) values(...)
		Photo photoSaved = repo.save(photoItem);

		// 리소스 생성됨
		res.setStatus(HttpServletResponse.SC_CREATED);

		// 추가된 객체를 반환
		return photoSaved;
	}

	@DeleteMapping(value = "/photos/{id}")
	public boolean removePhoto(@PathVariable long id, HttpServletResponse res) {

		// id에 해당하는 객체가 없으면
		// Optional null-safe 용으로 자바 1.8에 나온 방식
		// repository.findby(id 값)
		// select * from photo where id = ?;
		Optional<Photo> photo = repo.findById(Long.valueOf(id));
		if (photo.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}

		// 삭제 수행
		// delete from photo where id = ?
		repo.deleteById(id);

		return true;
	}

	@PutMapping(value = "/photos/{id}")
	public Photo modifyPhoto(@PathVariable long id, @RequestBody Photo photo, HttpServletResponse res) {

		// id에 해당하는 객체가 없으면
		Optional<Photo> photoItem = repo.findById(Long.valueOf(id));
		if (photoItem.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		// 타이틀이 빈값
		if (TextProcesser.isEmpyText(photo.getTitle())) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		// 파일URL이 빈값
		if (TextProcesser.isEmpyText(photo.getPhotoUrl())) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		Photo photoToSave = photoItem.get();

		photoToSave.setTitle(photo.getTitle());
		photoToSave.setDescription(TextProcesser.getPlainText(photo.getDescription()));
		photoToSave.setPhotoUrl(photo.getPhotoUrl());
		photoToSave.setFileType(photo.getFileType());
		photoToSave.setFileName(photo.getFileName());

		// repository.save(entity) : id 가 있으면 업데이트, 없으면 insert
		// update set title=값, description=값....
		// where id = ?
		Photo photoSaved = repo.save(photoToSave);
		return photoSaved;
	}

	// 포토 하위에 댓글 추가
	// POST / photos/{photoId}/comments
	// POST / photos/1/comments {"content": "댓글 내용입니다"}
	// id 가 1인 photo에 하위 레코드 comment 추가

//	@GetMapping(value="/photos/{photoId}/comments")
//	public PhotoComment addPhotoComment(@PathVariable long photoId, @RequestBody PhotoComment comment) {
//		comment.set
//		return null;
//	}
}