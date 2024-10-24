import React, { useState } from "react";
import { View, StyleSheet, Button, Image, Text } from "react-native";

export default function App5() {
  const [userData, setUserData] = useState();

  const getUserData = async () => {
    const response = await fetch("https://api.github.com/search/users?q=David");
    const data = await response.json();

    if (data.items && data.items.length > 0) {
      setUserData(data.items[0]);
    } else {
      console.log("No se encontraron usuarios.");
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
      <Button title="PULSAME!" onPress={getUserData}></Button>
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
});
