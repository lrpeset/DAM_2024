import { useState } from "react";
import { Pressable, View, StyleSheet, TextInput, Text, Alert } from "react-native";

export default function App04() {
  const [input, setInput] = useState("");

  const handlePress = () => {
    if (input === "") {
      Alert.alert("No se ha introducido nada");
    } else if (!isNaN(input)) {
      Alert.alert("Número introducido");
    } else {
      Alert.alert("String introducido");
    }
  };

  return (
    <View style={styles.container}>
      <TextInput style={styles.input} placeholder="Inserta tu texto..." onChangeText={(text) => setInput(text)} value={input} />
      <Pressable style={styles.button} onPress={handlePress}>
        <Text style={styles.text}>Pulsa...</Text>
      </Pressable>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "white",
  },
  input: {
    height: 40,
    borderColor: "gray",
    borderWidth: 1,
    marginBottom: 20,
    width: 200,
    paddingHorizontal: 10,
  },
  button: {
    backgroundColor: "blue",
    padding: 10,
  },
  text: {
    fontSize: 16,
    color: "white",
  },
});
