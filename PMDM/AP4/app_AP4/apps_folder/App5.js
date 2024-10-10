import { StyleSheet, View } from "react-native";

export default function App5() {
  const circleSizes = [
    { size: 100, color1: "blue", color2: "red" },
    { size: 75, color1: "blue", color2: "red" },
    { size: 50, color1: "blue", color2: "red" },
  ];

  return (
    <View style={styles.container}>
      {circleSizes.map((circle, index) => (
        <View key={index}>
          <View style={styles.row}>
            <View
              style={[
                styles.quarterCircle,
                {
                  backgroundColor: circle.color1,
                  width: circle.size,
                  height: circle.size,
                  borderTopLeftRadius: circle.size * 1.5,
                },
              ]}
            />
            <View
              style={[
                styles.quarterCircle,
                {
                  backgroundColor: circle.color2,
                  width: circle.size,
                  height: circle.size,
                  borderTopRightRadius: circle.size * 1.5,
                },
              ]}
            />
          </View>
          <View style={styles.row}>
            <View
              style={[
                styles.quarterCircle,
                {
                  backgroundColor: circle.color2,
                  width: circle.size,
                  height: circle.size,
                  borderBottomLeftRadius: circle.size * 1.5,
                },
              ]}
            />
            <View
              style={[
                styles.quarterCircle,
                {
                  backgroundColor: circle.color1,
                  width: circle.size,
                  height: circle.size,
                  borderBottomRightRadius: circle.size * 1.5,
                },
              ]}
            />
          </View>
        </View>
      ))}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  row: {
    flexDirection: "row",
  },
});
