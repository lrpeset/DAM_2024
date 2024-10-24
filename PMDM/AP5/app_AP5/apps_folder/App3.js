import React, { useState } from "react";
import { View, SafeAreaView, StyleSheet, TextInput, Button, Alert, Text } from "react-native";

export default function App3() {
  const [inputValue, setInputValue] = useState("");
  const [numbersArray, setNumbersArray] = useState([]);
  const [stringsArray, setStringsArray] = useState([]);

  const handleButtonPress = () => {
    const number = parseFloat(inputValue);

    if (!isNaN(number)) {
      setNumbersArray([...numbersArray, number]);
    } else if (inputValue === "") {
      Alert.alert("Error", "Por favor, inserta al menos un número");
    } else {
      setStringsArray([...stringsArray, inputValue]);
    }
    setInputValue("");
  };

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

        <View style={styles.arrayContainer}>
          {numbersArray.map((num, index) => (
            <Text key={index} style={styles.numberText}>
              {num}
            </Text>
          ))}
        </View>

        <View style={styles.arrayContainer}>
          {stringsArray.map((str, index) => (
            <Text key={index} style={styles.stringText}>
              {str}
            </Text>
          ))}
        </View>
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
  arrayContainer: {
    marginTop: 20,
    alignItems: "center",
  },
  numberText: {
    fontSize: 16,
    marginVertical: 5,
  },
  stringText: {
    fontSize: 16,
    marginVertical: 5,
    color: "blue",
  },
});