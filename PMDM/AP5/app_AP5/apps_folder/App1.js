import React, { useState } from "react";
import { View, SafeAreaView, StyleSheet, TextInput, Button, Alert, Text } from "react-native";

export default function App1() {
  const [inputValue, setInputValue] = useState("");
  const [numbersArray, setNumbersArray] = useState([]);

  const handleButtonPress = () => {
    const number = parseFloat(inputValue);

    if (!isNaN(number)) {
      setNumbersArray([...numbersArray, number]);
      setInputValue("");
    } else if (inputValue === "") {
      Alert.alert("Error", "Por favor, inserta al menos un número");
    } else {
      Alert.alert(
        "Error",
        "Por favor, no introduzcas Strings, tienes que insertar valores numéricos"
      );
    }
  };

  const numbersString = numbersArray.join(", ");

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.innerContainer}>
        <TextInput
          style={styles.input}
          placeholder="Inserta tu texto..."
          value={inputValue}
          onChangeText={setInputValue}
        />
        <Button title="PULSA..." onPress={handleButtonPress} />

        <Text style={styles.numberText}>{numbersString}</Text>
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  innerContainer: {
    width: "80%",
    alignItems: "center",
  },
  input: {
    height: 40,
    margin: 12,
    padding: 10,
    borderWidth: 1,
    width: "100%",
  },
  numberText: {
    fontSize: 16,
    marginVertical: 5,
  },
});
