# Assignment Notes

### Week1 Percolation

思路
+ `Percolation.java`

 1. n*n的grid，加上顶部和底部两个虚拟节点，需要一个size为(n*n + 2)的并查集`uf`.
 2. 上课讲`UF`数据结构时使用的TestClient，每次都给出一个数对pair(p, q)，就直接进行一次`uf.union()`操作；而这里则是，每次指定一个site要求`open()`它，open操作需要触发`uf.union()`操作——即“动态连通性”问题，每打开一个格子，都可能改变整个grid的连通性，所以要及时更新`uf`对象的信息（然而一开始竟然吧`uf.union()`放在了构造函数中，还良久没看出问题……）
 3. 明确了构造函数`Percolation()`、`open()`怎么做，其余几个函数就都是一两行代码了，当然还要加上异常处理。
 4. 另外需(cai)要(guo)注(le)意(keng)的是，每一处二位下标和一维下标的正确填写。
 
另外在打印置信区间时，用的printf("95%...", ...)，由于`%`没有转义，导致`java.util.FormatFlagsConversionMismatchException`，也找了很久原因。
 
+ `PercolationStats.java`

 这个类的职责就是计算mu, sigma, 和置信区间。Specification中已经给出了计算公式，显而易见，这三个值计算都是依赖于样本值的得出，也就是`x[]`数组。而`x[]`数组的每个元素，都来自一次模拟试验，实验过程即：反复open随机的一个site，直至整个grid能够`percolates()`，这时统计共open了多少个site、占site总数比例为多少，即可。