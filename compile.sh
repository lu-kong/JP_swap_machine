cd ~/Documents/Swap_pricing/;
javac -d ./bin/ ./src/SwapMachine.java ./src/basics/*.java ./src/exchangers/*.java ./src/orderbook/*.java;
cd ./bin/;
jar cf SwapMachineTest.jar ./src/*;
jar cfe SwapMachineTest.jar src.SwapMachine ./src/SwapMachine.class ./src/basics/*.class ./src/exchangers/*.class ./src/orderbook/*.class;

