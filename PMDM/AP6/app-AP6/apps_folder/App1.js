import React, { useState } from "react";
import { Button, View, StyleSheet, Image } from "react-native";

export default function App1() {
  const [catImage, setCatImage] = useState(null);

  const getCatImage = async () => {
    const res = await fetch("https://api.thecatapi.com/v1/images/search?size=full");
    const data = await res.json();
    setCatImage(data[0].url);
  };

  return (
    <View style={styles.container}>
      {catImage && <Image source={{ uri: catImage }} style={styles.image} />}
      <Button title="Mostrar gato" onPress={getCatImage} />
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
