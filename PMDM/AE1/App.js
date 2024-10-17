import React, { useState } from "react";
import { Text, View, Pressable } from "react-native";

export default function App() {
  const [input, setInput] = useState("");
  const [displayValue, setDisplayValue] = useState("0");
  const [result, setResult] = useState("");

  const formatDisplayValue = (value) => value.toString().slice(0, 11);

  const handleInput = (value) => {
    if (result !== "") {
      resetInput(value);
      return;
    }

    if (isMaxLengthExceeded(displayValue, value)) return;

    if (value === ",") {
      handleCommaInput();
      return;
    }

    isNumber(value) ? updateDisplayValue(value) : clearDisplayValue();

    appendInput(value);
  };

  const resetInput = (value) => {
    setResult("");
    setInput(value);
    setDisplayValue(formatDisplayValue(value));
  };

  const isMaxLengthExceeded = (displayValue, value) => {
    return displayValue.length >= (displayValue.includes(".") ? 12 : 11) && isNumber(value);
  };

  const handleCommaInput = () => {
    if (!displayValue.includes(".")) {
      setDisplayValue((prevDisplay) => prevDisplay + ".");
      setInput((prevInput) => prevInput + ".");
    }
  };

  const clearDisplayValue = () => {
    setDisplayValue("");
  };

  const updateDisplayValue = (value) => {
    const newValue = (prevDisplay) => (prevDisplay === "0" ? value : prevDisplay + value);
    setDisplayValue(newValue(displayValue));
  };

  const appendInput = (value) => {
    setInput((prevInput) => prevInput + value);
  };

  const handleOperation = (operation) => {
    if (input) {
      const lastNumber = parseFloat(input.split(/[\+\-\*\/]/).pop());
      const result = operation(lastNumber);
      setDisplayOrError(result);
    }
  };

  const setDisplayOrError = (result) => {
    if (isNaN(result)) {
      setResult("Error");
      setDisplayValue("Error");
    } else {
      const limitedResult = limitTo11Digits(result);
      setDisplayValue(limitedResult);
      setResult(result.toString());
      setInput("");
    }
  };

  const calculateResult = () => {
    const sanitizedInput = input.replace(/,/g, ".").replace("x", "*");
    const evalResult = eval(sanitizedInput);
    setResult(evalResult.toString());
    setInput("");
    setDisplayValue(limitTo11Digits(evalResult));
  };

  const calculatePi = () => {
    const piValue = Math.PI.toFixed(10);
    handleInput(piValue);
    setDisplayValue(piValue);
  };

  const unaryOperations = {
    sen: (x) => Math.sin(x),
    cos: (x) => Math.cos(x),
    tan: (x) => Math.tan(x),
    "√": (x) => (x >= 0 ? Math.sqrt(x) : NaN),
    "1/X": (x) => (x !== 0 ? 1 / x : NaN),
    "!": (x) => (x >= 0 ? factorialFunction(Math.floor(x)) : NaN),
    ln: (x) => (x > 0 ? Math.log(x) : NaN),
    log: (x) => (x > 0 ? Math.log10(x) : NaN),
    rad: (x) => convertDegreesToRadians(x),
    deg: (x) => convertRadiansToDegrees(x),
  };

  const limitTo11Digits = (num) => {
    const strNum = num.toString();
    const limit = strNum.includes(".") ? 12 : 11;
    return strNum.length > limit ? strNum.slice(0, limit) : strNum;
  };

  const factorialFunction = (n) => (n <= 1 ? 1 : n * factorialFunction(n - 1));

  const clearInput = () => {
    setInput("");
    setResult("");
    setDisplayValue("0");
  };

  const convertDegreesToRadians = (degrees) => degrees * (Math.PI / 180);
  const convertRadiansToDegrees = (radians) => radians * (180 / Math.PI);

  const isNumber = (value) => !isNaN(value) || value === "." || value === ",";

  const buttons = [
    ["sen", "cos", "tan", "deg"],
    ["ln", "log", "π", "rad"],
    ["1/X", "!", "√", "/"],
    ["7", "8", "9", "x"],
    ["4", "5", "6", "-"],
    ["1", "2", "3", "+"],
    ["C", "0", ",", "="],
  ];

  return (
    <View
      style={{
        justifyContent: "center",
        alignSelf: "center",
        marginVertical: 80,
      }}
    >
      <Text style={{ fontSize: 45, fontWeight: "bold" }}>Calculadora</Text>

      <View style={{ marginTop: 5 }}>
        <View
          style={{
            flexDirection: "row",
            marginBottom: 10,
            height: 70,
            width: 340,
            borderRadius: 4,
            borderWidth: 1,
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <Text
            style={{
              fontSize: 50,
              textAlign: "right",
              flex: 1,
              paddingRight: 10,
            }}
          >
            {displayValue}
          </Text>
        </View>

        {buttons.map((row, rowIndex) => (
          <View key={rowIndex} style={{ flexDirection: "row" }}>
            {row.map((value, index) => (
              <PressableButton
                key={index}
                value={value}
                onPress={() => handleButtonPress(value)}
                isNumber={isNumber(value)}
              />
            ))}
          </View>
        ))}
      </View>
    </View>
  );

  function handleButtonPress(value) {
    if (value === "C") return clearInput();
    if (value === "=") return calculateResult();
    if (value === "π") return calculatePi();
    if (unaryOperations[value]) return handleOperation(unaryOperations[value]);
    handleInput(value);
  }
}

const PressableButton = ({ value, onPress, isNumber }) => (
  <Pressable onPress={onPress} style={buttonStyle(isNumber)}>
    <Text style={{ fontSize: 24, color: "white" }}>{value}</Text>
  </Pressable>
);

const buttonStyle = (isNumber) => ({
  borderRadius: 8,
  justifyContent: "center",
  alignItems: "center",
  width: 80,
  height: 80,
  backgroundColor: isNumber ? "blue" : "gray",
  margin: 3,
});
