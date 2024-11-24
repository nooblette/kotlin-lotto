package lotto.client

import lotto.Lotto
import lotto.LottoMachine
import lotto.statistics.Profit
import lotto.statistics.WinningStatistics
import lotto.view.InputView
import lotto.view.ResultView

class LottoClient(
    private val lottoMachine: LottoMachine,
) {
    fun run() {
        val amount = InputView.inputPurchaseAmount()
        val lottoCount = getLottoCount(amount)
        ResultView.printLottoCount(lottoCount)

        val lottos = lottoMachine.generate(lottoCount)
        ResultView.printLottoList(lottos)

        val winningNumbers = InputView.inputLastWeekWinningNumbers()
        val lottoRanks = WinningStatistics(purchasedLottos = lottos, winningNumbers = winningNumbers).ranks
        ResultView.printStatistics(lottoRanks = lottoRanks)

        val profit = Profit(winningAmount = lottoRanks.sumOf { it.prize }, purchaseAmount = amount).yield()
        ResultView.printProfit(profit)
    }

    private fun getLottoCount(purchaseAmount: Int): Int = (purchaseAmount / Lotto.PRICE)
}
