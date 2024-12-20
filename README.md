# Diary App

A feature-rich diary application built with **Kotlin** and **Android Studio**. This app allows users to create, edit, and delete diary entries, and displays the entries in an intuitive grid format. Key features include multi-selection for deletion, a persistent database using **Room**, and a calendar for quick date-based navigation.

---

## Features

1. **Add New Entries**
   - Create a new diary entry with a title, content, and automatically associated date.

2. **Edit Existing Entries**
   - Update the title or content of an existing diary entry.

3. **Delete Entries**
   - Long-press to enable multi-selection and delete selected entries.

4. **Calendar Integration**
   - Quickly view entries for a specific date using a calendar.

5. **Persistent Storage**
   - All entries are stored locally using **Room Database**, ensuring data persists across app sessions.

6. **Grid Display**
   - Entries are displayed in a visually appealing grid format with a preview of their title, date, and content.

---

## Screenshots

### Home Screen
![Home Screen](/images/home_screen.jpg)

### Add Entry Screen
![Add Entry](/images/add_entry_screen_1.jpg)
![Add Entry](/images/add_entry_screen_2.jpg)
![Add Entry](/images/add_entry_screen_3.jpg)

### Edit Entry Screen
![Edit Entry](/images/edit_entry_screen_1.jpg)
![Edit Entry](/images/edit_entry_screen_2.jpg)
![Edit Entry](/images/edit_entry_screen_3.jpg)

### Multi-Select Delete
![Multi-Select Delete](/images/multi_select_delete_screen_1.jpg)
![Multi-Select Delete](/images/multi_select_delete_screen_2.jpg)

---

## Technologies Used

- **Programming Language**: Kotlin
- **UI Framework**: Android XML layouts
- **Database**: Room Database
- **Navigation**: Intents and View Binding
- **UI Components**: RecyclerView, ConstraintLayout, BottomNavigationView
- **Material Design**: CardView, FloatingActionButton

---

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/diary-app.git
   ```

2. Open the project in **Android Studio**.

3. Sync Gradle and resolve any dependencies.

4. Build and run the project on an emulator or physical device.

---

## Future Enhancements

- Add cloud sync functionality for cross-device support.
- Include search functionality to filter entries.
- Add reminders for specific entries.

---

## Contributing

Contributions are welcome! If you have suggestions for improvements or new features, please submit an issue or create a pull request.

---

## License

This project is licensed under the MIT License. See the LICENSE file for details.

