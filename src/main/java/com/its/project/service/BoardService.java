package com.its.project.service;

import com.its.project.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
  private final BoardRepository boardRepository;

}
