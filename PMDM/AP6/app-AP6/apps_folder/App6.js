import React, { useState } from "react";
import { View, StyleSheet, Button, Image, Text, TextInput } from "react-native";

export default function App6() {
  const [users, setUsers] = useState([]);
  const [userData, setUserData] = useState(null);
  const [searchIndex, setSearchIndex] = useState("");

  const getUserData = async () => {
    const response = await fetch("https://api.github.com/search/users?q=David");
    const data = await response.json();

    if (data.items && data.items.length > 0) {
      setUsers(data.items);
    } else {
      console.log("No se encontraron usuarios.");
    }
  };

  const searchUserByIndex = () => {
    const index = parseInt(searchIndex, 10);
    if (index >= 0) {
      setUserData(users[index]);
    } else {
      console.log("Índice fuera de rango.");
      setUserData(null);
    }
  };

  return (
    <View style={styles.container}>
      {userData && (
        <View style={styles.userContainer}>
          <Image source={{ uri: userData.avatar_url }} style={styles.image} />
          <Text style={styles.text}> Login: {userData.login}</Text>
          <Text style={styles.text}>ID: {userData.id}</Text>
        </View>
      )}
      <TextInput
        style={styles.input}
        placeholder="Ingrese el índice del usuario"
        value={searchIndex}
        onChangeText={setSearchIndex}
      />
      <Button title="REALIZAR BÚSQUEDA!" onPress={searchUserByIndex}></Button>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#f5f5f5",
  },
  userContainer: {
    alignItems: "center",
    marginTop: 20,
  },
  image: {
    width: 300,
    height: 300,
    marginTop: 20,
    borderRadius: 10,
  },
  text: {
    marginTop: 8,
    fontSize: 16,
  },
  input: {
    height: 30,
    marginTop: 10,
    borderWidth: 1,
    width: "100%",
  },
});
