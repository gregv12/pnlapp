# Pnl Calculator

The docker application wraps a pnl calculator that operates in real time listening to changes in the file system to 
calculate positions and pnl. 

## Running the app

Make sure you have docker installed and running then execute the command

```shell
docker compose up

[+] Running 6/6
 ✔ pnlImage Pulled                                                                                                                                                                                  54.8s 
   ✔ 286c1c922769 Pull complete                                                                                                                                                                     31.5s 
   ✔ da5f8d4b6eb4 Pull complete                                                                                                                                                                     31.7s 
   ✔ 4695d300e5f0 Pull complete                                                                                                                                                                     51.9s 
   ✔ 1fe2660538b0 Pull complete                                                                                                                                                                     52.0s 
   ✔ 4f4fb700ef54 Pull complete                                                                                                                                                                     52.0s 
[+] Running 2/2
 ✔ Network pnlapp_default       Created                                                                                                                                                              0.0s 
 ✔ Container pnlapp-pnlImage-1  Created                                                                                                                                                              0.5s 
Attaching to pnlImage-1
pnlImage-1  | 11:05:37.632 [main] WARN   eventAudit #STARTING FLUXTION SERVER
pnlImage-1  | 11:05:37.779 [heartBeatProcessor-thread] INFO   EventQueueToEventProcessorAgent.heartBeatProcessor-thread/positionSnapshot/onEventCallBack registerProcessor: com.fluxtion.server.lib.pnl.calculator.FluxtionPnlCalculator@74c79fa2
pnlImage-1  | 11:05:37.780 [heartBeatProcessor-thread] INFO   EventQueueToEventProcessorAgent.heartBeatProcessor-thread/symbolFeed/onEventCallBack registerProcessor: com.fluxtion.server.lib.pnl.calculator.FluxtionPnlCalculator@74c79fa2
pnlImage-1  | 11:05:37.781 [heartBeatProcessor-thread] INFO   EventQueueToEventProcessorAgent.heartBeatProcessor-thread/rateFeed/onEventCallBack registerProcessor: com.fluxtion.server.lib.pnl.calculator.FluxtionPnlCalculator@74c79fa2
pnlImage-1  | 11:05:37.781 [heartBeatProcessor-thread] INFO   EventQueueToEventProcessorAgent.heartBeatProcessor-thread/tradeEventFeed/onEventCallBack registerProcessor: com.fluxtion.server.lib.pnl.calculator.FluxtionPnlCalculator@74c79fa2
pnlImage-1  | 11:05:37.781 [heartBeatProcessor-thread] INFO   EventQueueToEventProcessorAgent.heartBeatProcessor-thread/positionSnapshot/onEventCallBack start
pnlImage-1  | 11:05:37.782 [heartBeatProcessor-thread] INFO   EventQueueToEventProcessorAgent.heartBeatProcessor-thread/symbolFeed/onEventCallBack start
pnlImage-1  | 11:05:37.782 [heartBeatProcessor-thread] INFO   EventQueueToEventProcessorAgent.heartBeatProcessor-thread/rateFeed/onEventCallBack start
pnlImage-1  | 11:05:37.782 [heartBeatProcessor-thread] INFO   EventQueueToEventProcessorAgent.heartBeatProcessor-thread/tradeEventFeed/onEventCallBack start

```

Input and output is located in the `app_var/docker-var` directory. 

## Outputs
The results are published as jsonl files, new lines are appended as the input updates
- Global mark to market and positions app_var/docker-var/results/netMtm.jsonl 
- Per instrument breakdown app_var/docker-var/results/instrumentMtm.jsonl 
- Position snapshot app_var/docker-var/data_in/positionSnapshot.jsonl 

## Inputs
The inputs are stored as jsonl files and constantly watched for inputs

- app_var/docker-var/data_in/trades.jsonl - trades that are used to update position calculations
- app_var/docker-var/data_in/rates.jsonl - rates that are used to calculate the mark to market profit and loss
- app_var/docker-var/data_in/symbols.jsonl - maps symbols to instruments
- app_var/docker-var/data_in/positionSnapshot.jsonl - used for restarts

## Updates
