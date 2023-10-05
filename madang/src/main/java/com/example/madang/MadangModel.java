package com.example.madang;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class MadangModel {
	@NonNull
	private String id;
}
