package com.duck.devhub.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "user_id")
		private Long id;

		@Column(name = "username", nullable = false, unique = true, length = 30)
		private String username;

		@Column(name = "email", unique = true, length = 255)
		private String email;

		@Column(name = "password_hash", nullable = false, length = 255)
		private String passwordHash;

		@Column(name = "created_at", nullable = false, updatable = false)
		private LocalDateTime createdAt;

		@Column(name = "updated_at", nullable = false)
		private LocalDateTime updatedAt;

		@Builder
		public User(String username, String email, String passwordHash) {
				this.username = username;
				this.email = email;
				this.passwordHash = passwordHash;
		}

		@PrePersist
		protected void onCreate() {
				LocalDateTime now = LocalDateTime.now();
				this.createdAt = now;
				this.updatedAt = now;
		}

		@PreUpdate
		protected void onUpdate() {
				this.updatedAt = LocalDateTime.now();
		}

		public void updateProfile(String username, String email) {
				this.username = username;
				this.email = email;
		}

		public void changePassword(String passwordHash) {
				this.passwordHash = passwordHash;
		}
}