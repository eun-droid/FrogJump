package com.yes.frogjump

import java.util.*
import kotlin.collections.ArrayList

val testCaseNumRange = 1..5
val stoneNumRange = 1..1000000
val coordinatesRange = 1..1000000000
val maxJumpDistanceRange = 1..1000000000

data class TestCaseInfo(
        var maxJumpDistance: Int,
        var stoneCoordinates: List<Int>
)

fun main() {
    val testCaseNum = getTestCaseNum()
    val testCaseInfoList: ArrayList<TestCaseInfo> = arrayListOf()

    for (index in 0 until testCaseNum) {
        val testCaseInfo = TestCaseInfo(0, arrayListOf())
        testCaseInfo.stoneCoordinates = getStoneCoordinates(getStoneNum())
        testCaseInfo.maxJumpDistance = getMaxJumpDistance()

        testCaseInfoList.add(testCaseInfo)
    }

    val startTime = Date().time

    var testCaseIndex = 1
    for (testCaseInfo in testCaseInfoList) {
        println("Case#${testCaseIndex++}")
        println(processMinJumpNum(testCaseInfo))
    }

    val endTime = Date().time
    val elapsed = endTime - startTime
    println("${elapsed}ms")
}

fun processMinJumpNum(testCaseInfo: TestCaseInfo): Int {
    var jumpNum = 0
    var currentCoordinate = 0
    var index = 1

    while (index < testCaseInfo.stoneCoordinates.size) {
        // 개구리가 마지막 돌로 이동하는 것이 불가능한 경우 체크
        if (testCaseInfo.stoneCoordinates[index] - testCaseInfo.stoneCoordinates[index-1] > testCaseInfo.maxJumpDistance) {
            jumpNum = -1
            break
        }

        val difference = testCaseInfo.stoneCoordinates[index] - currentCoordinate
        when {
            // 최대 점프 거리와 같은 경우
            difference == testCaseInfo.maxJumpDistance -> {
                currentCoordinate = testCaseInfo.stoneCoordinates[index]
                jumpNum++
            }
            // 최대 점프 거리보다 큰 경우
            difference > testCaseInfo.maxJumpDistance -> {
                index--
                currentCoordinate = testCaseInfo.stoneCoordinates[index]
                jumpNum++
            }
            // 잔여 점프
            index == testCaseInfo.stoneCoordinates.lastIndex -> {
                jumpNum++
            }
        }

        index++
    }

    return jumpNum
}

fun getMaxJumpDistance(): Int {
    var jumpDistance: Int

    do {
        jumpDistance = readLine()?.toInt() ?: 0
    } while (jumpDistance !in maxJumpDistanceRange)

    return jumpDistance
}

fun getStoneCoordinates(stoneNum: Int): ArrayList<Int> {
    val stoneCoordinates: ArrayList<Int> = arrayListOf()

    var isValid = false
    do {
        stoneCoordinates.clear()
        stoneCoordinates.add(0)   // 첫 돌은 항상 0 좌표에 있다.

        val coordinatesString = readLine() ?: ""
        if (coordinatesString.isEmpty())
            continue

        val coordinatesIntList = coordinatesString.split(" ").map { it.toInt() }
        if (coordinatesIntList.count() + 1/*0번 돌*/ != stoneNum)
            continue

        stoneCoordinates.addAll(coordinatesIntList)

        var index = 1
        var lastValue = 0
        var currentValue = 0
        while (index <= stoneCoordinates.lastIndex) {
            currentValue = stoneCoordinates[index]
            if (currentValue <= lastValue)
                break;
            if (currentValue !in coordinatesRange)
                break;
            index++
            lastValue = currentValue
            if (index > stoneCoordinates.lastIndex)
                isValid = true
        }

    } while (!isValid)

    return stoneCoordinates
}

fun getStoneNum(): Int {
    var stoneNum: Int

    do {
        stoneNum = readLine()?.toInt() ?: 0
    } while (stoneNum !in stoneNumRange)

    return stoneNum + 1 // 돌은 0번 좌표에도 하나 있다.
}

fun getTestCaseNum() : Int {
    var testCaseNum: Int

    do {
        testCaseNum = readLine()?.toInt() ?: 0
    } while (testCaseNum !in testCaseNumRange)

    return testCaseNum
}