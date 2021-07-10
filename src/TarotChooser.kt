import kotlin.system.exitProcess
import kotlin.random.Random

var major = mutableListOf("The Fool", "The Magician", "The High Priestess", "The Empress", "The Emperor", "The Hierophant", "The Lovers", "The Chariot",
    "Strength", "The Hermit", "Wheel of Fortune", "Justice", "The Hanged Man", "Death", "Temperance", "The Devil", "The Tower", "The Star", "The Moon", "The Sun",
    "Judgement", "The World") // 대 아르카나 카드 배열
var pickedMajor = mutableListOf<String>()
var pickedMinor = mutableListOf<Card>() // 소 아르카나 중복 방지 리스트 선언
val minorNum = mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Page", "Knight", "Queen", "King") // 소 아르카나 숫자
val minorSuit = mutableListOf("Wands", "Cups", "Swords", "Pentacles") // 소 아르카나 수트
val direction = listOf("정방향", "역방향") // 방향 리스트

fun main() {
    val arcana = input("arcana")
    val counts = input("counts").toInt()
        for (i in 0 until counts) { // 반복
            when (arcana) {
                "대" -> {
                    major()
                }
                "소" -> {
                    pickedMinor.add(minor()) // 중복이 아닐 경우 pickedMinor 리스트에 추가
                }
            }
        }
    for (i in pickedMajor.indices) {
        println(pickedMajor[i])
    }
    for (i in pickedMinor.indices) {
        println("${pickedMinor[i].number} | ${pickedMinor[i].suit} | ${pickedMinor[i].direction}") // 구조체 이용 출력
    }
    exit() // 종료
}

data class Card(val number: String, val suit : String, val direction : String) // 소 아르카나 데이터 클래스

fun input(para : String) : String {
    var toReturn = ""
    when (para) {
        "arcana" -> {
            print("어떤 아르카나를 사용합니까? (대/소) : ")
            var arcana = readLine()!!
            if (!(arcana == "대" || arcana == "소")) { // 입력값이 "대" 또는 "소"가 아닐 경우
                println("잘못된 아르카나 입력입니다. 다시 입력해 주세요.\n")
                arcana = input("arcana")
            }
            toReturn = arcana
        }
        "counts" -> {
            print("몇장의 카드를 뽑습니까? 정수로 입력해주세요. : ")
            var counts = readLine()!!
            if (counts.toIntOrNull() == null) {
                println("잘못된 수 입력입니다. 다시 입력해주세요.\n")
                counts = input("counts")
            }
            toReturn = counts
        }
        "major" -> {
            var input = readLine()!!// 입력 받음
            if (input.toIntOrNull() == null || !(input.toInt() - 1 in major.indices)) { // 인덱스가 범위 외일 경우
                println("잘못된 입력입니다. 다시 선택하십시오\n")
                input = input("major")
            }
            toReturn = input
        }
        "minorNum" -> {
            print("1부터 ${minorNum.size} 사이의 수를 정수로 입력해주세요. : ") // 숫자 인덱스 입력
            var input = readLine()!!
            if (input.toIntOrNull() == null || !(input.toInt() - 1 in minorNum.indices)) {
                println("잘못된 수 입력입니다. 다시 입력해주세요.\n")
                input = input("minorNum")
            }
            toReturn = input
        }
        "minorSuit" -> {
            print("1부터 ${minorSuit.size} 사이의 수를 정수로 입력해주세요. : ") // 수트 인덱스 입력
            var input = readLine()!!
            if (input.toIntOrNull() == null || !(input.toInt() - 1 in minorSuit.indices)) {
                println("잘못된 수 입력입니다. 다시 입력해주세요.\n")
                input = input("minorSuit")
            }
            toReturn = input
        }
    }
    return toReturn
}

fun major() {
    major.shuffle() // 대 아르카나 카드 섞음
    print("1부터 ${major.size} 사이의 수를 정수로 입력해주세요. : ") // 1장 뽑기 위한 입력값 받기
    val input = input("major").toInt()
    pickedMajor.add("${major.removeAt(input - 1)} | ${direction[Random.nextInt(2)]}") // 스트링 추가 (중복 가능성이 없으므로 중복 방지 없음)
}


fun minor() : Card {
    minorNum.shuffle() // 소 아르카나 숫자 리스트 섞음
    minorSuit.shuffle() // 소 아르카나 수트 리스트 섞음
    val input = input("minorNum").toInt() // 입력 받음
    val input2 = input("minorSuit").toInt() // 입력 받음
    var struct = Card(number = minorNum[input - 1], suit = minorSuit[input2 - 1], direction = direction[Random.nextInt(2)]) // 데이터 클래스로 구조체 만듬
    for (j in pickedMinor.indices) { // 브루트 포스 중복 검사
        if (struct.number in pickedMinor[j].number && struct.suit in pickedMinor[j].suit) { // 구조체의 숫자와 수트가 모두 동일할 경우
            println("동일 카드가 나왔습니다. 1회 더 선택해주세요.\n")
            struct = minor()
        }
    }
    return struct
}

fun exit() {
    print("\nEnter를 눌러 종료합니다. 또는 c를 입력해 다시 시작합니다. : ")
    val input = readLine()!!
    if (input == "c") {
        pickedMajor = mutableListOf<String>()
        pickedMinor = mutableListOf<Card>()
        major = mutableListOf("The Fool", "The Magician", "The High Priestess", "The Empress", "The Emperor", "The Hierophant", "The Lovers", "The Chariot",
            "Strength", "The Hermit", "Wheel of Fortune", "Justice", "The Hanged Man", "Death", "Temperance", "The Devil", "The Tower", "The Star", "The Moon", "The Sun",
            "Judgement", "The World")
        main()
    } else {
        exitProcess(0) // 정상 종료
    }
}