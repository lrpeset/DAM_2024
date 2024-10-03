import { useState } from "react";
import { Text, Pressable, Image, View, StyleSheet } from "react-native";
import image1 from "../assets/favicon.png";
import image2 from "../assets/icon.png";

export default function App02() {
  const [image1State, setImage1State] = useState(image1);
  const [text1State, setText1State] = useState("Texto 1");
  const [image2State, setImage2State] = useState(image2);
  const [text2State, setText2State] = useState("Texto 2");

  const handlePress = (id) => {
    if (id === 1) {
      setImage1State((prevImage) => (prevImage === image1 ? image2 : image1));
      setText1State((prevText) => (prevText === "Texto 1" ? "Texto 1 Cambiado" : "Texto 1"));
    } else if (id === 2) {
      setImage2State((prevImage) => (prevImage === image1 ? image2 : image1));
      setText2State((prevText) => (prevText === "Texto 2" ? "Texto 2 Cambiado" : "Texto 2"));
    }
  };

  return (
    <View style={styles.containerRow}>
      <Pressable onPress={() => handlePress(1)}>
        <Image style={styles.Image} source={image1State} />
        <Text>{text1State}</Text>
      </Pressable>
      <Pressable onPress={() => handlePress(2)}>
        <Image style={styles.Image} source={image2State} />
        <Text>{text2State}</Text>
      </Pressable>
    </View>
  );
}

const styles = StyleSheet.create({
  containerRow: {
    flex: 1,
    backgroundColor: "white",
    alignItems: "center",
    justifyContent: "center",
    flexDirection: "row",
  },
  Image: {
    width: 100,
    height: 100,
  },
});
