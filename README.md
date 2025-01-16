# 📚 LibLoans

LibLoans is an Android application developed as part of my **Advanced Mobile Application Development** module during my first semester of 4th year. 

The app is an **online library system** designed for TUS students across all campuses, enabling them to:
- Create an account
- Search the library catalog
- Loan books
- Return borrowed books
- Locate the nearest TUS campus library 📍

---

## 🚀 Features

1. **Account Management**: Create an account and log in securely using Firebase Authentication.
2. **Library Search**: Search for books by title or filter by genre.
3. **Loan Books**: Borrow books and manage loaned items easily.
4. **Return Books**: Return books you've finished or are due back.
5. **Find Libraries**: Locate the nearest TUS campus library using Google Maps integration.

---

## 📱 App Showcase

### 🏠 Home Page
![Home Page](https://github.com/user-attachments/assets/888bc949-544e-4b8d-8e84-288bdb28bf43)

### 🔍 Search The Library
![Search Library](https://github.com/user-attachments/assets/8ebc1807-6912-4a0c-a0e9-faa7a0068188)

### 📖 View Book Details
![Book Details](https://github.com/user-attachments/assets/75edd8d2-322b-43c6-8ec6-2047591dcd4a)

### 📂 Check Your Loans
![Check Loans](https://github.com/user-attachments/assets/9ba34f00-84a1-4883-840f-0c50457ea8cc)

### 📦 Return Books
![Return Books](https://github.com/user-attachments/assets/16b6adc9-cd98-4c15-93c3-e9b01738479b)

### 🗺️ Locate Your Nearest TUS Library
![Locate Library](https://github.com/user-attachments/assets/e988921d-b687-42e9-86d0-5466a3dd1db9)

---

## 📜 Development Timeline & Commits

### ✅ **Commit 1: Initial Setup**
- Basic app structure initialized.
- Welcome screen created.

---

### ✅ **Commit 2: Core Screens Added**
- Login, Register, Home, Search, and Loans screens implemented.
- Firebase Authentication added for Login and Register functionality.
- Maps screen set up but under construction.

---

### ✅ **Commit 2.5: Bug Fixes & Code Cleanup**
- Fixed crashing issues by re-adding Google Services dependencies.
- Added comments throughout the codebase for better readability.

---

### ✅ **Commit 3: Search and Loan System**
- Integrated Firestore DB for book data storage.
- Implemented book search by title and genre filter.
- Book details page added.
- Borrowing functionality introduced, including a **3-week loan period** with real-time date selection.
- Return screen implemented for users to return loaned books.

---

### ✅ **Commit 4: Final Enhancements**
- Added book cover images to all relevant screens (Search, Loans, Returns, and Details).
- Designed an app logo and applied consistent branding across the app.
- Added site-wide CSS for a cohesive UI/UX.
- Removed unused files to clean up the codebase.

---

## 💡 Technologies Used

- **Kotlin**: Primary language for app development.
- **Firebase Authentication**: For secure login and registration.
- **Firestore**: Database for storing book and user data.
- **Google Maps API**: For locating nearby TUS libraries.
- **Coil**: For image loading and display.

---

Feel free to explore the code and provide feedback! 😊
