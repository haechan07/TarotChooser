import kotlin.system.exitProcess
import kotlin.random.Random

fun main() {
    val major = mutableListOf("The Fool", "The Magician", "The High Priestess", "The Empress", "The Emperor", "The Hierophant", "The Lovers", "The Chariot",
    "Strength", "The Hermit", "Wheel of Fortune", "Justice", "The Hanged Man", "Death", "Temperance", "The Devil", "The Tower", "The Star", "The Moon", "The Sun",
    "Judgement", "The World") // 대 아르카나 카드 배열
    val pickedMajor = mutableListOf<String>()
    val pickedMinor = mutableListOf<Card>() // 소 아르카나 중복 방지 리스트 선언
    val minorNum = mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Page", "Knight", "Queen", "King") // 소 아르카나 숫자
    val minorSuit = mutableListOf("Wands", "Cups", "Swords", "Pentacles") // 소 아르카나 수트
    val direction = listOf("정방향", "역방향") // 방향 리스트
    print("어떤 아르카나를 사용합니까? (대/소) : ")
    val arcana = readLine()
    if (!(arcana == "대" || arcana == "소")) { // 입력값이 "대" 또는 "소"가 아닐 경우
        println("잘못된 입력입니다. 종료합니다.")
        done()
    }
    print("몇장의 카드를 뽑습니까? 정수로 입력해주세요. : ")
    try {
        var counts = readLine()!!.toInt()
        cont@ for (i in 0 until counts) { // 반복
            when (arcana) {
                "대" -> {
                    major.shuffle() // 대 아르카나 카드 섞음
                    print("1부터 ${major.size} 사이의 수를 정수로 입력해주세요. : ") // 1장 뽑기 위한 입력값 받기
                    try {
                        val input = readLine()!!.toInt() // 입력 받음
                        if (!(input - 1 in major.indices)) { // 인덱스가 범위 외일 경우
                            println("잘못된 입력입니다. 종료합니다.")
                            done() // 종료
                        }
                        pickedMajor.add("${major.removeAt(input - 1)} | ${direction[Random.nextInt(2)]}") // 스트링 추가 (중복 가능성이 없으므로 중복 방지 없음)
                    } catch (e : NumberFormatException) {
                        println("잘못된 입력입니다. 종료합니다.") // 입력값 변환 실패시
                        done() // 종료
                    }
                }
                "소" -> {
                    minorNum.shuffle() // 소 아르카나 숫자 리스트 섞음
                    minorSuit.shuffle() // 소 아르카나 수트 리스트 섞음
                    print("1부터 ${minorNum.size} 사이의 수를 정수로 입력해주세요. : ") // 숫자 인덱스 입력
                    try {
                        val input = readLine()!!.toInt() // 입력 받음
                        if (!(input - 1 in minorNum.indices)) { // 인덱스가 범위 외일 경우
                            println("잘못된 입력입니다. 종료합니다.")
                            done() // 종료
                        }
                        print("1부터 ${minorSuit.size} 사이의 수를 정수로 입력해주세요. : ") // 수트 인덱스 입력
                        try {
                            val input2 = readLine()!!.toInt() // 입력 받음
                            val struct = Card(number = minorNum[input], suit = minorSuit[input2], direction = direction[Random.nextInt(2)]) // 데이터 클래스로 구조체 만듬
                           for (j in pickedMinor.indices) { // 브루트 포스 중복 검사
                                if (struct.number in pickedMinor[j].number && struct.suit in pickedMinor[j].suit) { // 구조체의 숫자와 수트가 모두 동일할 경우
                                    counts++ // 1회 더 반복할 것
                                    continue@cont // continue문으로 바깥 반복문으로 다시 이동
                                }
                            }
                            pickedMinor.add(struct) // 중복이 아닐 경우 pickedMinor 리스트에 추가
                        } catch (e : NumberFormatException) { // input2 변환 오류 발생 시
                            println("잘못된 입력입니다. 종료합니다.")
                            done() // 종료
                        }
                    } catch (e : NumberFormatException) { // input 변환 오류 발생 시
                        println("잘못된 입력입니다. 종료합니다.")
                        done() // 종료
                    }
                }
            }
        }
    } catch (e : NumberFormatException) { // counts 변환 오류 발생 시
        println("잘못된 입력입니다. 종료합니다.")
        done() // 종료
    }
    for (i in pickedMajor.indices) {
        println(pickedMajor[i]) // 스트링 빌더에서 영감 얻은 방식으로
    }
    for (i in pickedMinor.indices) {
        println("${pickedMinor[i].number} | ${pickedMinor[i].suit} | ${pickedMinor[i].direction}") // 구조체 이용 출력
    }
    print("\nEnter를 눌러 종료합니다.")
    readLine()
    exitProcess(0) // 정상 종료
}

data class Card(val number: String, val suit : String, val direction : String) // 소 아르카나 데이터 클래스

fun done() {
    print("\nEnter를 눌러 종료합니다.")
    readLine()
    exitProcess(-1) // 비정상 종료
}