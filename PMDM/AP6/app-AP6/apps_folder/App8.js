import React, { useState } from "react";
import { View, StyleSheet, Button, TextInput, Text, ScrollView } from "react-native";

export default function App() {
  const [books, setBooks] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedBook, setSelectedBook] = useState(null);
  const [searchIndex, setSearchIndex] = useState("");

  const getBooksData = async () => {
    try {
      const response = await fetch(`https://openlibrary.org/search.json?q=${searchTerm}`);
      const data = await response.json();

      if (data.docs && data.docs.length > 0) {
        setBooks(data.docs);
      } else {
        console.log("No se encontraron libros.");
      }
    } catch (error) {
      console.log("Error en la solicitud:", error);
    }
  };

  const searchBookByIndex = () => {
    const index = parseInt(searchIndex, 10);
    if (index >= 0 && index < books.length) {
      setSelectedBook(books[index]);
    } else {
      console.log("Índice fuera de rango.");
      setSelectedBook(null);
    }
  };

  return (
    <View style={styles.container}>
      <TextInput
        style={styles.input}
        placeholder="Ingresa el término de búsqueda"
        value={searchTerm}
        onChangeText={setSearchTerm}
      />
      <Button title="Buscar libros" onPress={getBooksData} />

      <TextInput
        style={styles.input}
        placeholder="Ingresa el índice del libro"
        value={searchIndex}
        onChangeText={setSearchIndex}
        keyboardType="numeric"
      />
      <Button title="Buscar Libro por Índice" onPress={searchBookByIndex} />

      {selectedBook && (
        <View style={styles.bookContainer}>
          <Text style={styles.text}>Título: {selectedBook.title}</Text>
          <Text style={styles.text}>Autor: {selectedBook.author_name?.join(", ")}</Text>
          <Text style={styles.text}>Año: {selectedBook.first_publish_year}</Text>
        </View>
      )}

      <ScrollView style={styles.scrollView}>
        {books.map((book, index) => (
          <Text key={index} style={styles.bookItem}>
            {index}. {book.title} - {book.author_name?.join(", ")}
          </Text>
        ))}
      </ScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#f5f5f5",
    padding: 16,
  },
  bookContainer: {
    alignItems: "center",
    marginTop: 20,
  },
  text: {
    marginTop: 10,
    fontSize: 16,
  },
  input: {
    height: 40,
    borderColor: "gray",
    borderWidth: 1,
    marginTop: 10,
    width: "100%",
    paddingHorizontal: 10,
  },
  scrollView: {
    marginTop: 20,
    width: "100%",
  },
  bookItem: {
    padding: 10,
    borderBottomWidth: 1,
    borderBottomColor: "#ccc",
  },
});
