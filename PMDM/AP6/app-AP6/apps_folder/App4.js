import React, { useState } from "react";
import { View, StyleSheet, Button, Image } from "react-native";

export default function App4() {
  const [userImage, setUserImage] = useState();

  const getUserImage = async () => {
    const response = await fetch("https://api.github.com/search/users?q=Java");
    const data = await response.json();

    if (data.items && data.items.length > 0) {
      setUserImage(data.items[0].avatar_url);
    } else {
      console.log("No se encontraron usuarios.");
    }
  };

  return (
    <View style={styles.container}>
      {userImage && <Image source={{ uri: userImage }} style={styles.image} />}
      <Button title="PULSAME!" onPress={getUserImage}></Button>
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
  image: {
    width: 300,
    height: 300,
    marginTop: 20,
    borderRadius: 10,
  },
});
